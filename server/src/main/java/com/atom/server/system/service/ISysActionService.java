package com.atom.server.system.service;

import com.atom.common.pojo.table.PageData;
import com.atom.common.pojo.table.TableData;
import com.atom.server.system.pojo.filter.SysActionFilter;
import com.atom.server.system.pojo.vo.SysActionTopicVO;
import com.atom.server.system.pojo.vo.SysActionVO;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author zr
 * @description 系统资源服务接口
 * @date 2021/4/23
 */
public interface ISysActionService {
	List<SysActionTopicVO> topicList();

	TableData<SysActionVO> actionList(SysActionFilter sysActionFilter, PageData pageData, HttpServletResponse response);
}
