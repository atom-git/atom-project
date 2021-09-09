package com.atom.server.system.service.impl;

import com.atom.common.pojo.http.RestRequest;
import com.atom.common.pojo.mapper.LogType;
import com.atom.common.pojo.table.PageData;
import com.atom.common.pojo.table.TableData;
import com.atom.common.security.SessionUser;
import com.atom.common.util.DownloadUtil;
import com.atom.server.system.dao.ISysLogDao;
import com.atom.server.system.entity.SysLog;
import com.atom.server.system.pojo.filter.SysLogFilter;
import com.atom.server.system.pojo.vo.SysLogVO;
import com.atom.server.system.service.ISysLogService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zr
 * @description 系统日志管理服务
 * @date 2021/4/22
 */
@Service
@Transactional
@Slf4j
public class SysLogService implements ISysLogService {

	/**
	 * 根据配置文件判断是否记录日志
	 */
	@Value("${atom.log.record}")
	private boolean ifLog;

	/**
	 * 系统日志dao
	 */
	@Resource
	private ISysLogDao sysLogDao;

	/**
	 * 系统日志编辑VO转换器
	 */
	private final SysLogVO.VOConverter sysLogVOConverter = new SysLogVO.VOConverter();

	/**
	 * 系统日志过滤Filter转换器
	 */
	private final SysLogFilter.FilterConverter sysLogFilterConverter = new SysLogFilter.FilterConverter();

	/**
	 * 获取系统日志数据表
	 * @param sysLogFilter 系统用户Filter对象
	 * @param pageData 分页信息
	 * @param response 请求响应
	 * @return 返回当前页的日志数据列表
	 */
	@Override
	public TableData<SysLogVO> list(SysLogFilter sysLogFilter, PageData pageData, HttpServletResponse response) {
		// 转换为查询对象
		DetachedCriteria dc = sysLogFilterConverter.doForward(sysLogFilter);
		// 查询列表
		List<SysLog> sysLogList = sysLogDao.findPage(dc, pageData);
		List<SysLogVO> sysLogVOList = sysLogList.stream().map(sysLogVOConverter :: doForward).collect(Collectors.toList());
		// 查询记录数
		long totalCnt = sysLogDao.countByDC(dc);
		// 如果是下载，则生成excel
		if (pageData.getDownload()) {
			DownloadUtil.downlodExcel("系统日志", SysLogVO.class, sysLogVOList, totalCnt, response);
			return new TableData<>(pageData, totalCnt);
		} else {
			return new TableData<>(pageData, sysLogVOList, totalCnt);
		}
	}

	/**
	 * 保存日志
	 * @param logType 日志类型
	 * @param restRequest 请求信息
	 */
	@Override
	public void save(SessionUser sessionUser, LogType logType, RestRequest restRequest) {
		if (ifLog) {
			SysLog sysLog = new SysLog(sessionUser, logType, restRequest);
			sysLogDao.save(sysLog);
		} else {
			log.info("配置不记录日志");
		}
	}
}
