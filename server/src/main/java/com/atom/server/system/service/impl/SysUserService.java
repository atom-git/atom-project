package com.atom.server.system.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.atom.common.pojo.GlobalConstant;
import com.atom.common.pojo.exception.BusException;
import com.atom.common.pojo.http.RestError;
import com.atom.common.pojo.mapper.IfValid;
import com.atom.common.pojo.table.PageData;
import com.atom.common.pojo.table.TableData;
import com.atom.common.security.SessionUser;
import com.atom.common.util.FileUtil;
import com.atom.server.system.dao.ISysRoleDao;
import com.atom.server.system.dao.ISysUserDao;
import com.atom.server.system.dao.ISysUserRoleDao;
import com.atom.server.system.entity.SysRole;
import com.atom.server.system.entity.SysUser;
import com.atom.server.system.entity.SysUserRole;
import com.atom.server.system.pojo.dto.AppConfigDTO;
import com.atom.server.system.pojo.dto.SysUserDTO;
import com.atom.server.system.pojo.filter.SysUserFilter;
import com.atom.server.system.pojo.vo.SysUserRoleVO;
import com.atom.server.system.pojo.vo.SysUserVO;
import com.atom.server.system.service.ISysUserService;
import com.google.gson.JsonObject;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zr
 * @description 系统用户管理服务
 * @date 2021/4/23
 */
@Service
@Transactional
public class SysUserService implements ISysUserService {

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
	 * 系统角色dao
	 */
	@Resource
	private ISysRoleDao sysRoleDao;

	/**
	 * 系统用户角色dao
	 */
	@Resource
	private ISysUserRoleDao sysUserRoleDao;

	/**
	 * 系统用户VO转换器
	 */
	private final SysUserVO.VOConverter sysUserVOConverter = new SysUserVO.VOConverter();

	/**
	 * 系统用户Filter转换器
	 */
	private final SysUserFilter.FilterConverter sysUserFilterConverter = new SysUserFilter.FilterConverter();

	/**
	 * 系统用户DTO转换器
	 */
	private final SysUserDTO.DTOConverter sysUserDTOConverter = new SysUserDTO.DTOConverter();

	/**
	 * 系统用户角色VO转换器
	 */
	private final SysUserRoleVO.VOConverter sysUserRoleVOConverter = new SysUserRoleVO.VOConverter();

	/**
	 * 更新用户密码
	 * @param userId 用户ID
	 * @param sysUserDTO 用户信息实体
	 */
	@Override
	public void updatePassword(Integer userId, SysUserDTO sysUserDTO) {
		// 校验用户原密码是否一致
		SysUser sysUser = sysUserDao.findOne(userId);
		if (Validator.isNotNull(sysUser) && Validator.isNotEmpty(sysUserDTO.getOriginPassword())) {
			if (passwordEncoder.matches(sysUserDTO.getOriginPassword(), sysUser.getPassword())) {
				// 设置新密码
				sysUser.setPassword(passwordEncoder.encode(sysUserDTO.getPassword()));
				sysUserDao.update(sysUser);
			} else {
				throw new AuthenticationServiceException(RestError.ERROR1007.getErrorMsg());
			}
		} else {
			throw new AuthenticationServiceException(RestError.ERROR1007.getErrorMsg());
		}
	}

	/**
	 * 更新用户头像
	 * @param userId 用户ID
	 * @param sysUserDTO 用户信息实体
	 */
	@Override
	public void updateHead(Integer userId, SysUserDTO sysUserDTO) {
		// 查询原用户
		SysUser sysUser = sysUserDao.findOne(userId);
		if (Validator.isNotNull(sysUser)) {
			// 设置为新的头像，页面需自行上传文件后再调用此接口
			sysUser.setHead(sysUserDTO.getHead());
			sysUserDao.update(sysUser);
		} else {
			throw new AuthenticationServiceException(RestError.ERROR1006.getErrorMsg());
		}
	}

	/**
	 * 获取系统用户数据表，或下载数据
	 * @param sysUserFilter 系统用户Filter对象
	 * @param pageData 分页信息
	 * @param response 请求响应
	 * @return 返回当前页的用户数据列表
	 */
	@Override
	public TableData<SysUserVO> list(SysUserFilter sysUserFilter, PageData pageData, HttpServletResponse response) {
		// 转换为查询对象
		DetachedCriteria dc = sysUserFilterConverter.doForward(sysUserFilter);
		// 查询列表
		List<SysUser> sysUserList = sysUserDao.findPage(dc, pageData);
		List<SysUserVO> sysUserVOList = sysUserList.stream().map(sysUserVOConverter::doForward).collect(Collectors.toList());
		// 查询记录数
		long totalCnt = sysUserDao.countByDC(dc);
		// 如果是下载，则生成excel
		if (pageData.getDownload()) {
			FileUtil.downlodExcel("系统用户", SysUserVO.class, sysUserVOList, totalCnt, response);
			return new TableData<>(pageData, totalCnt);
		} else {
			return new TableData<>(pageData, sysUserVOList, totalCnt);
		}
	}

	/**
	 * 新增或者编辑用户
	 * @param sysUserDTO 用户传输dto
	 * @return 新增时返回用户默认密码，修改时返回为空
	 */
	@Override
	public String saveOrUpdate(SysUserDTO sysUserDTO) {
		SysUser sysUser = sysUserDTOConverter.doForward(sysUserDTO);
		if (Validator.isNotNull(sysUser.getId())) {
			// 编辑用户:用户是否存在
			SysUser originUser = sysUserDao.findOne(sysUser.getId());
			if (Validator.isNull(originUser)) {
				throw new BusException(RestError.ERROR1006);
			}
			originUser.setPhone(sysUserDTO.getPhone());
			originUser.setName(sysUserDTO.getName());
			originUser.setDeptId(sysUserDTO.getDeptId());
			originUser.setUpdateTime(DateUtil.date());
			originUser.setIfValid(sysUserDTO.getIfValid());
			sysUserDao.update(originUser);
			return "";
		} else {
			// 判断account是否重复
			if (sysUserDao.exist("account", sysUser.getAccount())) {
				throw new BusException(RestError.ERROR1001);
			}
			// 新增用户:设置平台类型为dashboard
			sysUser.setPlatform(GlobalConstant.PLATFORM_DASHBOARD);
			String password = RandomUtil.randomString(GlobalConstant.PASSWORD_BASE_STR, 8);
			sysUser.setPassword(passwordEncoder.encode(password));
			sysUser.setCreateTime(DateUtil.date());
			sysUserDao.save(sysUser);
			return password;
		}
	}

	/**
	 * 重置用户密码
	 * @param userId 用户id
	 * @return 返回是否重置成功
	 */
	@Override
	public String resetPassword(Integer userId) {
		// 查询用户是否存在
		SysUser sysUser = sysUserDao.findOne(userId);
		if (Validator.isNotNull(sysUser)) {
			// 重置密码并返回
			String password = RandomUtil.randomString(GlobalConstant.PASSWORD_BASE_STR, 8);
			sysUser.setPassword(passwordEncoder.encode(password));
			return password;
		} else {
			throw new BusException(RestError.ERROR1006);
		}
	}

	/**
	 * 用户禁用/启用
	 * @param userId 用户id
	 */
	@Override
	public void toggleValid(Integer userId) {
		// 查询用户是否存在
		SysUser sysUser = sysUserDao.findOne(userId);
		if (Validator.isNotNull(sysUser)) {
			Integer ifValid = sysUser.getIfValid().equals(IfValid.VALID.getCode()) ? IfValid.INVALID.getCode() : IfValid.VALID.getCode();
			sysUser.setIfValid(ifValid);
			sysUserDao.update(sysUser);
		} else {
			throw new BusException(RestError.ERROR1006);
		}
	}

	/**
	 * 查询用户角色信息
	 * @param userId 用户id
	 * @return 返回角色列表及用户角色信息
	 */
	@Override
	public SysUserRoleVO roleList(Integer userId) {
		// 查询用户
		SysUser sysUser = sysUserDao.findOne(userId);
		if (Validator.isNull(sysUser)) {
			throw new BusException(RestError.ERROR1006);
		}
		// 查询角色列表
		List<SysRole> sysRoleList = sysRoleDao.findAll();
		// 查询用户角色列表
		List<SysUserRole> sysUserRoleList = sysUserRoleDao.findAllByField("userId", userId);
		return sysUserRoleVOConverter.doForward(sysUser, sysRoleList, sysUserRoleList);
	}

	/**
	 * 更新用户角色
	 * @param userId 用户id
	 * @param userRoleList 角色列表
	 */
	@Override
	public void updateRole(Integer userId, Integer[] userRoleList) {
		// 查询用户
		SysUser sysUser = sysUserDao.findOne(userId);
		if (Validator.isNull(sysUser)) {
			throw new BusException(RestError.ERROR9000, "请确认用户是否有效");
		}
		// 查询用户角色列表
		List<SysUserRole> originUserRoleList = sysUserRoleDao.findAllByField("userId", userId);
		if (Validator.isNotNull(originUserRoleList) && originUserRoleList.size() > 0) {
			sysUserRoleDao.deleteAll(originUserRoleList);
		}
		// 保存新的用户角色数据
		if (Validator.isNotNull(userRoleList) && userRoleList.length > 0) {
			List<SysUserRole> sysUserRoleList = new ArrayList<>();
			for(Integer roleId : userRoleList) {
				SysUserRole sysUserRole = new SysUserRole(userId, roleId);
				sysUserRoleList.add(sysUserRole);
			}
			sysUserRoleDao.save(sysUserRoleList);
		}
	}

	/**
	 * 更新用户App配置
	 * @param sessionUser 用户信息
	 * @param appConfigDTO app配置DTO
	 */
	@Override
	public void updateAppConfig(SessionUser sessionUser, AppConfigDTO appConfigDTO) {
		SysUser sysUser = sysUserDao.findOne(sessionUser.getId());
		if (Validator.isNull(sysUser)) {
			throw new BusException(RestError.ERROR9000, "请确认用户是否有效");
		}
		// 设置用户App配置
		sysUser.setAppConfig(JSONObject.toJSONString(appConfigDTO));
		sysUserDao.update(sysUser);
	}
}
