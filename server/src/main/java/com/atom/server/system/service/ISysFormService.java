package com.atom.server.system.service;

import com.atom.common.pojo.table.PageData;
import com.atom.common.pojo.table.TableData;
import com.atom.common.security.SessionUser;
import com.atom.server.system.pojo.dto.SysFormDTO;
import com.atom.server.system.pojo.filter.SysFormFilter;
import com.atom.server.system.pojo.vo.SysFormVO;

/**
 * @author zr
 * @description 系统自定义表单服务接口
 * @date 2021/11/18
 */
public interface ISysFormService {
	TableData<SysFormVO> list(SysFormFilter sysFormFilter, PageData pageData);

	void saveOrUpdate(SessionUser sessionUser, SysFormDTO sysFormDTO);

	void toggleValid(Integer formId);
}
