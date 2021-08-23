package com.atom.server.system.service.impl;

import com.atom.common.pojo.mapper.NewsType;
import com.atom.common.pojo.table.PageData;
import com.atom.common.pojo.table.TableData;
import com.atom.common.security.SessionUser;
import com.atom.common.util.FileUtil;
import com.atom.server.system.dao.ISysNewsDao;
import com.atom.server.system.entity.SysNews;
import com.atom.server.system.entity.SysUser;
import com.atom.server.system.pojo.filter.SysNewsFilter;
import com.atom.server.system.pojo.vo.SysNewsVO;
import com.atom.server.system.pojo.vo.SysUserVO;
import com.atom.server.system.pojo.vo.UserNewsVO;
import com.atom.server.system.service.ISysNewsService;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zr
 * @description 系统通知消息待办管理服务
 * @date 2021/8/23
 */
@Service
@Transactional
public class SysNewsService implements ISysNewsService {

	/**
	 * 系统通知消息待办dao
	 */
	@Resource
	private ISysNewsDao sysNewsDao;

	/**
	 * 系统通知消息待办VO转换器
	 */
	private final SysNewsVO.VOConverter sysNewsVOConverter = new SysNewsVO.VOConverter();

	/**
	 * 用户通知消息待办VO转换器
	 */
	private final UserNewsVO.VOConverter userNewsVOConverter = new UserNewsVO.VOConverter();

	/**
	 * 系统通知消息待办Filter转换器
	 */
	private final SysNewsFilter.FilterConverter sysNewsFilterConverter = new SysNewsFilter.FilterConverter();

	/**
	 * 订阅用户提醒消息待办信息
	 * @param sessionUser 用户信息，SessionUser为java.security.Principal的了类，因此可以注入
	 * @return 系统提醒消息待办列表
	 */
	@Override
	public UserNewsVO fetchNews(SessionUser sessionUser) {
		// 订阅fetch默认仅取10条
		PageData pageData = new PageData();
		// 获取用户通知
		List<SysNews> noticeList = sysNewsDao.findValidByUser(sessionUser.getId(), NewsType.NOTICE, pageData);
		// 获取用户消息
		List<SysNews> messageList = sysNewsDao.findValidByUser(sessionUser.getId(), NewsType.MESSAGE, pageData);
		// 获取用户待办
		List<SysNews> todoList = sysNewsDao.findValidByUser(sessionUser.getId(), NewsType.TODO, pageData);

		// 转换为VO
		return userNewsVOConverter.doForward(noticeList, messageList, todoList);
	}

	/**
	 * 获取系统提醒消息待办列表
	 * @param sysNewsFilter 系统提醒消息待办过滤对象
	 * @param pageData 分页信息
	 * @param response 请求响应
	 * @return 系统提醒消息待办列表
	 */
	@Override
	public TableData<SysNewsVO> list(SysNewsFilter sysNewsFilter, PageData pageData, HttpServletResponse response) {
		// 转换为查询对象
		DetachedCriteria dc = sysNewsFilterConverter.doForward(sysNewsFilter);
		// 查询列表
		List<SysNews> sysNewsList = sysNewsDao.findPage(dc, pageData);
		List<SysNewsVO> sysNewsVOList = sysNewsList.stream().map(sysNewsVOConverter::doForward).collect(Collectors.toList());
		// 查询记录数
		long totalCnt = sysNewsDao.countByDC(dc);
		// 如果是下载，则生成excel
		if (pageData.getDownload()) {
			FileUtil.downlodExcel("系统消息", SysNewsVO.class, sysNewsVOList, totalCnt, response);
			return new TableData<>(pageData, totalCnt);
		} else {
			return new TableData<>(pageData, sysNewsVOList, totalCnt);
		}
	}
}
