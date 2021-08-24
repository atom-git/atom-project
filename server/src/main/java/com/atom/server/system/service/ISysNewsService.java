package com.atom.server.system.service;

import com.atom.common.pojo.table.PageData;
import com.atom.common.pojo.table.TableData;
import com.atom.common.security.SessionUser;
import com.atom.server.system.pojo.filter.SysNewsFilter;
import com.atom.server.system.pojo.vo.SysNewsVO;
import com.atom.server.system.pojo.vo.UserNewsVO;

import javax.servlet.http.HttpServletResponse;

/**
 * @author zr
 * @description 系统通知消息待办管理服务接口
 * @date 2021/8/23
 */
public interface ISysNewsService {
	UserNewsVO fetchNews(SessionUser sessionUser);

	TableData<SysNewsVO> list(SysNewsFilter sysNewsFilter, PageData pageData, HttpServletResponse response);

	void read(Integer... newsIds);
}
