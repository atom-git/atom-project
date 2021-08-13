package com.atom.server.system.service;

import com.atom.common.pojo.table.PageData;
import com.atom.common.pojo.table.TableData;
import com.atom.server.system.pojo.dto.SysTypeCodeDTO;
import com.atom.server.system.pojo.dto.SysTypeDTO;
import com.atom.server.system.pojo.filter.SysTypeCodeFilter;
import com.atom.server.system.pojo.filter.SysTypeFilter;
import com.atom.server.system.pojo.vo.SysTypeCodeVO;
import com.atom.server.system.pojo.vo.SysTypeVO;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author zr
 * @description 系统数据字典管理服务接口
 * @date 2021/4/23
 */
public interface ISysTypeService {

	TableData<SysTypeVO> list(SysTypeFilter sysTypeFilter, PageData pageData, HttpServletResponse response);

	void saveOrUpdate(SysTypeDTO sysTypeDTO);

	void delete(Integer typeId);

	List<SysTypeCodeVO> codeList(SysTypeCodeFilter sysTypeCodeFilter);

	List<SysTypeCodeVO> codeTree(SysTypeCodeFilter sysTypeCodeFilter);

	SysTypeCodeVO saveOrUpdateCode(Integer meanId, SysTypeCodeDTO sysTypeCodeDTO);

	void deleteCode(Integer codeId);

	SysTypeCodeVO exchange(String move, SysTypeCodeDTO sysTypeCodeDTO);

}
