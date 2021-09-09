package com.atom.server.system.service.impl;

import com.atom.common.pojo.table.PageData;
import com.atom.common.pojo.table.TableData;
import com.atom.common.util.DownloadUtil;
import com.atom.server.system.dao.ISysActionDao;
import com.atom.server.system.dao.ISysActionTopicDao;
import com.atom.server.system.entity.SysAction;
import com.atom.server.system.entity.SysActionTopic;
import com.atom.server.system.pojo.filter.SysActionFilter;
import com.atom.server.system.pojo.vo.SysActionTopicVO;
import com.atom.server.system.pojo.vo.SysActionVO;
import com.atom.server.system.service.ISysActionService;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zr
 * @description 系统资源服务
 * @date 2021/4/23
 */
@Service
@Transactional
public class SysActionService implements ISysActionService {

	/**
	 * 动作资源dao
	 */
	@Resource
	private ISysActionDao sysActionDao;

	/**
	 * 动作资源主题dao
	 */
	@Resource
	private ISysActionTopicDao sysActionTopicDao;

	/**
	 * 动作资源VO转换器
	 */
	private final SysActionVO.VOConverter sysActionVOConverter = new SysActionVO.VOConverter();

	/**
	 * 动作资源主题转换器
	 */
	private final SysActionTopicVO.VOConverter sysActionTopicVOConverter = new SysActionTopicVO.VOConverter();

	/**
	 * 系统动作filter转换器
	 */
	private final SysActionFilter.FilterConverter sysActionFilterConverter = new SysActionFilter.FilterConverter();

	/**
	 * 查询动作资源主题列表
	 * @return 返回动作资源主题列表
	 */
	@Override
	public List<SysActionTopicVO> topicList() {
		List<SysActionTopic> sysActionTopicList = sysActionTopicDao.findAll();
		return sysActionTopicList.stream().map(sysActionTopicVOConverter :: doForward).collect(Collectors.toList());
	}

	/**
	 * 查询动作资源列表
	 * @param sysActionFilter 动作资源过滤条件
	 * @param pageData 分页条件
	 * @param response 响应
	 * @return 返回列表
	 */
	@Override
	public TableData<SysActionVO> actionList(SysActionFilter sysActionFilter, PageData pageData, HttpServletResponse response) {
		// 转换为查询对象
		DetachedCriteria dc = sysActionFilterConverter.doForward(sysActionFilter);
		// 查询列表
		List<SysAction> sysActionList = sysActionDao.findPage(dc, pageData);
		List<SysActionVO> sysActionVOList = sysActionList.stream().map(sysActionVOConverter :: doForward).collect(Collectors.toList());
		// 查询记录数
		long totalCnt = sysActionDao.countByDC(dc);
		// 如果是下载，则生成excel
		if (pageData.getDownload()) {
			DownloadUtil.downlodExcel("系统动作资源", SysActionVO.class, sysActionVOList, totalCnt, response);
			return new TableData<>(pageData, totalCnt);
		} else {
			return new TableData<>(pageData, sysActionVOList, totalCnt);
		}
	}
}
