package com.atom.server.system.service;

import com.atom.common.pojo.http.RestRequest;
import com.atom.common.pojo.mapper.LogType;
import com.atom.common.pojo.table.PageData;
import com.atom.common.pojo.table.TableData;
import com.atom.common.security.SessionUser;
import com.atom.server.system.pojo.filter.SysLogFilter;
import com.atom.server.system.pojo.vo.SysLogVO;

import javax.servlet.http.HttpServletResponse;

/**
 * @author zr
 * @description 系统日志管理服务接口
 * @date 2021/4/22
 */
public interface ISysLogService {

	TableData<SysLogVO> list(SysLogFilter sysLogFilter, PageData pageData, HttpServletResponse response);

	void save(SessionUser sessionUser, LogType logType, RestRequest restRequest);
}
