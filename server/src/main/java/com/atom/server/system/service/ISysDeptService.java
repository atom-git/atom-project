package com.atom.server.system.service;

import com.atom.server.system.pojo.dto.SysDeptDTO;
import com.atom.server.system.pojo.vo.SysDeptVO;

import java.util.List;

/**
 * @author zr
 * @description 系统组织机构管理服务接口
 * @date 2021/4/23
 */
public interface ISysDeptService {

	List<SysDeptVO> tree();

	void saveOrUpdate(SysDeptDTO sysDeptDTO);

	void delete(Integer deptId);
}
