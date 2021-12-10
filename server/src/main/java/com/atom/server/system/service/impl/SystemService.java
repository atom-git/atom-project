package com.atom.server.system.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.atom.common.pojo.GlobalConstant;
import com.atom.common.pojo.ThirdLogin;
import com.atom.common.pojo.http.RestError;
import com.atom.common.pojo.mapper.PlatformType;
import com.atom.common.security.SessionUser;
import com.atom.common.security.cache.IUserCacheStore;
import com.atom.common.util.RedisUtil;
import com.atom.common.util.TreeUtil;
import com.atom.server.system.dao.*;
import com.atom.server.system.entity.*;
import com.atom.server.system.pojo.dto.CaptchaDTO;
import com.atom.server.system.pojo.dto.ForgetDTO;
import com.atom.server.system.pojo.dto.SignInDTO;
import com.atom.server.system.pojo.dto.SignUpDTO;
import com.atom.server.system.pojo.vo.CaptchaVO;
import com.atom.server.system.pojo.vo.SysDeptVO;
import com.atom.server.system.pojo.vo.SysMenuVO;
import com.atom.server.system.service.ISystemService;
import com.wf.captcha.GifCaptcha;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zr
 * @description 系统登录、注册、获取验证码、校验验证码、忘记密码、登出公共模块服务
 * @date 2021/4/22
 */
@Service
@Transactional
public class SystemService implements ISystemService {

	/**
	 * 密码编码器
	 */
	@Resource
	private PasswordEncoder passwordEncoder;

	/**
	 * 系统用户dao
	 */
	@Resource
	private ISysUserDao sysUserDao;

	/**
	 * 系统用户角色关系dao
	 */
	@Resource
	private ISysUserRoleDao sysUserRoleDao;

	/**
	 * 系统角色菜单关系dao
	 */
	@Resource
	private ISysRoleMenuDao sysRoleMenuDao;

	/**
	 * 系统菜单dao
	 */
	@Resource
	private ISysMenuDao sysMenuDao;

	/**
	 * 系统角色资源关系dao
	 */
	@Resource
	private ISysRoleActionDao sysRoleActionDao;

	/**
	 * 系统资源dao
	 */
	@Resource
	private ISysActionDao sysActionDao;

	/**
	 * 用户缓存器
	 */
	@Resource
	private IUserCacheStore userCacheStore;

	/**
	 * 系统组织VO转换器
	 */
	private final SysDeptVO.VOConverter sysDeptVOConverter = new SysDeptVO.VOConverter();

	/**
	 * 系统菜单VO转换器
	 */
	private final SysMenuVO.VOConverter sysMenuVOConverter = new SysMenuVO.VOConverter();

	/**
	 * 系统注册DTO转换器
	 */
	private final SignUpDTO.DTOConverter signUpDTOConverter = new SignUpDTO.DTOConverter();

	/**
	 * 系统登录
	 * @param platform 平台是portal/dashboard
	 * @param signInDTO 登录的用户信息
	 */
	@Override
	public SessionUser signIn(String platform, SignInDTO signInDTO) {
		SysUser sysUser;
		// 判断登录模式是帐户登录或者手机验证码登录
		if ("account".equals(signInDTO.getType())) {
			// 帐号模式查询用户是否存在
			sysUser = sysUserDao.findByAccount(platform, signInDTO.getAccount());
			// 判断用户密码是否正常
			if (Validator.isNull(sysUser) || !passwordEncoder.matches(signInDTO.getPassword(), sysUser.getPassword())) {
				throw new UsernameNotFoundException(RestError.ERROR1000.getErrorMsg());
			}
		} else {
			// 手机号模式查询用户是否存在
			sysUser = sysUserDao.findByPhone(platform, signInDTO.getPhone());
			// 判断验证码是否正确
			if (Validator.isNull(sysUser) || Validator.isNull(signInDTO.getVerifyCode()) || !signInDTO.getVerifyCode().equals(RedisUtil.get(signInDTO.getAccount()))) {
				throw new UsernameNotFoundException(RestError.ERROR1008.getErrorMsg());
			}
		}
		// 更新用户最后登录时间
		sysUser.setLastLogin(DateUtil.date());
		sysUserDao.update(sysUser);
		// 创建用户SessionUser
		return this.buildSessionUser(sysUser, platform);
	}

	/**
	 * 根据sysUser构建SessionUser
	 * @param sysUser 系统用户
	 * @param platform 平台是portal/dashboard
	 * @return 返回SessionUser
	 */
	private SessionUser buildSessionUser(SysUser sysUser, String platform) {
		if (GlobalConstant.PLATFORM_DASHBOARD.equals(platform)) {
			return this.buildDashboardUser(sysUser);
		} else {
			return this.buildPortalUser(sysUser);
		}
	}

	/**
	 * 根据sysUser构建管理后台SessionUser
	 * @param sysUser 系统用户
	 * @return 返回SessionUser
	 */
	private SessionUser buildDashboardUser(SysUser sysUser) {
		// 取用户所属组织机构
		SysDept sysDept = sysUser.getSysDept();
		if (Validator.isNull(sysDept)) {
			throw new AuthenticationServiceException(RestError.ERROR1004.getErrorMsg());
		}
		// 用户组织信息
		SysDeptVO sysDeptVO = sysDeptVOConverter.doForward(sysDept);
		// 用户组织相关数据权限依据列表
		Set<Integer> sysDeptIdSet = TreeUtil.tileTree(Collections.singletonList(sysDept), SysDept::getChildren)
				.stream()
				.map(SysDept::getId)
				.collect(Collectors.toSet());
		// 取用户角色信息
		List<SysUserRole> sysUserRoleList = sysUserRoleDao.findAllByField("userId", sysUser.getId());
		Set<Integer> sysRoleIdSet = new HashSet<>();
		if (Validator.isNull(sysUserRoleList) || sysUserRoleList.size() <= 0) {
			throw new AuthenticationServiceException(RestError.ERROR1004.getErrorMsg());
		}
		sysUserRoleList.forEach(sysUserRole -> sysRoleIdSet.add(sysUserRole.getRoleId()));
		// 取用户所拥有的角色对应的菜单信息
		List<SysRoleMenu> sysRoleMenuList = sysRoleMenuDao.findByRoleIds(sysRoleIdSet);
		Set<Integer> roleMenuIdSet = new HashSet<>();
		if (Validator.isNull(sysRoleMenuList) || sysRoleMenuList.size() <= 0) {
			throw new AuthenticationServiceException(RestError.ERROR1004.getErrorMsg());
		}
		sysRoleMenuList.forEach(sysRoleMenu -> roleMenuIdSet.add(sysRoleMenu.getMenuId()));
		// 查询用户菜单
		List<SysMenu> sysMenuList = sysMenuDao.findValidByIds(roleMenuIdSet);
		if (Validator.isNull(sysMenuList) || sysMenuList.size() <= 0) {
			throw new AuthenticationServiceException(RestError.ERROR1004.getErrorMsg());
		}
		// 转换菜单VO
		List<SysMenuVO> sysMenuVOList = sysMenuList.stream()
				.map(sysMenuVOConverter::doForward)
				.collect(Collectors.toList());
		// 转换菜单树
		List<SysMenuVO> sysMenuTree = TreeUtil.polymerizationTree(sysMenuVOList, SysMenuVO::getId, SysMenuVO::getParentId, SysMenuVO::setChildren);
		// 取用户所拥有的角色对应的资源权限，默认是没有限制都选中，
		List<SysRoleAction> sysRoleActionList = sysRoleActionDao.findByRoleIds(sysRoleIdSet);
		Set<Integer> sysRoleActionIdSet = new HashSet<>();
		sysRoleActionList.forEach(sysRoleAction -> sysRoleActionIdSet.add(sysRoleAction.getActionId()));
		List<SysAction> sysActionList = sysActionDao.findByIds(sysRoleActionIdSet);
		// 查询默认赋权的部分
		List<SysAction> autoSysActionList = sysActionDao.findAutos();
		// 转换资源VO
		List<String> actionList;
		if (Validator.isNotNull(sysActionList) && sysActionList.size() > 0) {
			if (Validator.isNotNull(autoSysActionList) && autoSysActionList.size() > 0) {
				sysActionList.addAll(autoSysActionList);
			}
			actionList = sysActionList.stream().map(SysAction::getUrl).collect(Collectors.toList());
		} else {
			actionList = new ArrayList<>();
		}
		// 构建SessionUser
		return new SessionUser(sysUser, sysDeptVO, sysDeptIdSet, sysRoleIdSet, sysMenuTree, actionList);
	}

	/**
	 * 根据sysUser构建门户SessionUser
	 * @param sysUser 系统用户
	 * @return 返回SessionUser
	 */
	private SessionUser buildPortalUser(SysUser sysUser) {
		return new SessionUser(sysUser);
	}

	/**
	 * 系统注册
	 * @param signUpDTO 系统注册DTO
	 */
	@Override
	public void signUp(SignUpDTO signUpDTO) {
		String verifyCode = RedisUtil.getString(signUpDTO.getPhone());
		// 校验短信验证码
		if (Validator.isNotEmpty(verifyCode) && verifyCode.equals(signUpDTO.getVerifyCode())) {
			// 检查帐户是否存在
			SysUser sysUser = sysUserDao.findByAccount(GlobalConstant.PLATFORM_PORTAL, signUpDTO.getAccount());
			if (Validator.isNotNull(sysUser)) {
				throw new AuthenticationServiceException(RestError.ERROR1001.getErrorMsg());
			}
			sysUser = sysUserDao.findByPhone(GlobalConstant.PLATFORM_PORTAL, signUpDTO.getPhone());
			if (Validator.isNotNull(sysUser)) {
				throw new AuthenticationServiceException(RestError.ERROR1009.getErrorMsg());
			}
			// 转换用户
			sysUser = signUpDTOConverter.doForward(signUpDTO);
			sysUser.setPassword(passwordEncoder.encode(sysUser.getPassword()));
			sysUserDao.save(sysUser);
		} else {
			throw new AuthenticationServiceException(RestError.ERROR1003.getErrorMsg());
		}
	}

	/**
	 * 获取图片验证码
	 * @return 返回验证码
	 */
	@Override
	public CaptchaVO captcha() {
		// 生成gif动态图片验证码
		GifCaptcha captcha = new GifCaptcha(130, 40);
		String captchaKey = IdUtil.simpleUUID();
		// 在缓存中写入验证码
		RedisUtil.setMap(GlobalConstant.CAPTCHA_CACHE, captchaKey, captcha.text());
		return new CaptchaVO(captchaKey, captcha.toBase64());
	}

	/**
	 * 校验图片验证码
	 * @param captchaDTO 系统图片验证码DTO
	 */
	@Override
	public Boolean judgeCaptcha(CaptchaDTO captchaDTO) {
		// 读取缓存
		String captcha = RedisUtil.getString(GlobalConstant.CAPTCHA_CACHE, captchaDTO.getKey());
		// 判断是否匹配，不考虑字符大小写
		return Validator.isNotEmpty(captcha) && captcha.equalsIgnoreCase(captchaDTO.getCaptcha());
	}

	/**
	 * 忘记密码
	 * @param forgetDTO 忘记密码DTO
	 */
	@Override
	public void forgetPassword(ForgetDTO forgetDTO) {
		String verifyCode = RedisUtil.getString(forgetDTO.getPhone());
		// 校验短信验证码
		if (Validator.isNotEmpty(verifyCode) && verifyCode.equals(forgetDTO.getVerifyCode())) {
			// 检查帐户是否存在
			SysUser sysUser = sysUserDao.findByAccount(GlobalConstant.PLATFORM_PORTAL, forgetDTO.getAccount());
			if (Validator.isNull(sysUser)) {
				throw new AuthenticationServiceException(RestError.ERROR1006.getErrorMsg());
			}
			// 更新用户密码
			sysUser.setPassword(passwordEncoder.encode(forgetDTO.getPassword()));
			sysUserDao.update(sysUser);
		} else {
			throw new AuthenticationServiceException(RestError.ERROR1003.getErrorMsg());
		}
	}

	/**
	 * 系统用户登出
	 * @param request 请求
	 * @param response 响应
	 */
	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		// 不为空时退出登录
		if (Validator.isNotNull(auth)) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		// TODO 查询是否允许多用户登录，不允许时清除缓存，仅清除同平台类型的登录信息
		PlatformType platformType = PlatformType.matchName(request.getHeader("Platform-Type"));
		String token = request.getHeader("Access-Token");
		if (!ObjectUtils.isEmpty(token) && platformType != null) {
			userCacheStore.unRegister(platformType, token);
		}
	}

	/**
	 * 第三方登录回调方法
	 * @param request 请求
	 * @param platform 平台参数 portal/dashboard
	 * @param signInDTO 第三方登录授权临时票据,crf校验串
	 * @return 第三方登录回调方法,返回token令牌
	 */
	@Override
	public SessionUser thirdSignIn(HttpServletRequest request, String platform, SignInDTO signInDTO) {
		// 校验state临时授权
		if (RedisUtil.exists(signInDTO.getState()) && Validator.isNotEmpty(signInDTO.getCode())) {
			// 判断第三方登录的方式
			String accessTokenUrl = "";
			String appId = "";
			String appSecret = "";
			String userUrl = "";
			if (GlobalConstant.THIRD_LOGIN_WECHAT.equals(signInDTO.getThirdType())) {
				accessTokenUrl = ThirdLogin.WechatLogin.ACCESS_TOKEN_URL;
				appId = ThirdLogin.WechatLogin.APP_ID;
				appSecret = ThirdLogin.WechatLogin.APP_SECRET;
				userUrl = ThirdLogin.WechatLogin.USER_URL;
			}
			// 封装获取第三方access_token请求url
			String accessHttpUrl = StrUtil.format(accessTokenUrl, appId, appSecret, signInDTO.getCode());
			JSONObject response = JSONObject.parseObject(HttpUtil.get(accessHttpUrl));
			if (Validator.isNotEmpty(response.getString("errcode")) && !response.getString("errcode").equals("0")) {
				// 抛出错误信息
				throw new AuthenticationServiceException(RestError.ERROR1005.getErrorMsg());
			} else {
				// 从response中获取accessToken并请求用户信息
				String accessTokenKey = "";
				String openIdKey = "";
				String nickNameKey = "";
				String headKey = "";
				if (GlobalConstant.THIRD_LOGIN_WECHAT.equals(signInDTO.getThirdType())) {
					accessTokenKey = ThirdLogin.WechatLogin.ACCESS_TOKEN_KEY;
					openIdKey = ThirdLogin.WechatLogin.OPEN_ID_KEY;
					nickNameKey = ThirdLogin.WechatLogin.NICK_NAME_KEY;
					headKey = ThirdLogin.WechatLogin.HEAD_KEY;
				}
				String openId = response.getString(openIdKey);
				// 获取用户信息
				String userHttpUrl = String.format(userUrl, response.getString(accessTokenKey), openId);
				JSONObject userResponse = JSONObject.parseObject(HttpUtil.get(userHttpUrl));
				if (Validator.isNotEmpty(userResponse.getString("errcode")) && !userResponse.getString("errcode").equals("0")) {
					// 抛出错误信息
					throw new AuthenticationServiceException(RestError.ERROR1005.getErrorMsg());
				} else {
					// 判断用户openId是否存在，并且更新用户信息
					SysUser sysUser = sysUserDao.findByAccount(platform, openId);
					// 如果不存在，则创建用户
					if (Validator.isNull(sysUser)) {
						sysUser = new SysUser();
						sysUser.setNickName(userResponse.getString(nickNameKey));
						sysUser.setName(userResponse.getString(nickNameKey));
						sysUser.setOpenId(openId);
						// 默认使用openId作为其account
						sysUser.setAccount(openId);
						// 上传用户头像
						sysUser.setHead(userResponse.getString(headKey));
						sysUser.setPlatform(platform);
						sysUser.setCreateTime(DateUtil.date());
					} else {
						sysUser.setNickName(userResponse.getString(nickNameKey));
						sysUser.setName(userResponse.getString(nickNameKey));
						sysUser.setHead(userResponse.getString(headKey));
						sysUser.setUpdateTime(DateUtil.date());
					}
					sysUserDao.saveOrUpdate(sysUser);
					// 创建SessionUser
					return this.buildSessionUser(sysUser, platform);
				}
			}
		} else {
			throw new AuthenticationServiceException(RestError.ERROR1005.getErrorMsg());
		}
	}

	/**
	 * 统计在线用户数推送给前台`
	 * @return 在线用户数
	 */
	@Override
	public int onlineUser() {
		// 在线用户数
		int onlineUser = 0;
		Set<Serializable> stompTokenKeys = RedisUtil.getRedisTemplate().keys("*_STOMP_TOKEN");
		if (stompTokenKeys != null && stompTokenKeys.size() > 0) {
			onlineUser = stompTokenKeys.stream().mapToInt(mapKey -> RedisUtil.getRedisTemplate().opsForHash().keys(mapKey).size()).sum();
		}
		return onlineUser;
	}
}
