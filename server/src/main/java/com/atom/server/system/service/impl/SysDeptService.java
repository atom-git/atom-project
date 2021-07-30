package com.atom.server.system.service.impl;

import cn.hutool.core.lang.Validator;
import com.atom.common.pojo.exception.BusException;
import com.atom.common.pojo.http.RestError;
import com.atom.common.pojo.mapper.IfValid;
import com.atom.common.util.TreeUtil;
import com.atom.server.system.dao.ISysDeptDao;
import com.atom.server.system.dao.ISysUserDao;
import com.atom.server.system.entity.SysDept;
import com.atom.server.system.entity.SysUser;
import com.atom.server.system.pojo.dto.SysDeptDTO;
import com.atom.server.system.pojo.vo.SysDeptVO;
import com.atom.server.system.service.ISysDeptService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zr
 * @description 系统组织机构管理服务
 * @date 2021/4/23
 */
@Service
@Transactional
public class SysDeptService implements ISysDeptService {

	/**
	 * 系统组织机构dao
	 */
	@Resource
	private ISysDeptDao sysDeptDao;

	/**
	 * 系统用户dao
	 */
	@Resource
	private ISysUserDao sysUserDao;

	/**
	 * 系统组织VO转换器
	 */
	private final SysDeptVO.VOConverter sysDeptVOConverter = new SysDeptVO.VOConverter();

	/**
	 * 系统组织DTO转换器
	 */
	private final SysDeptDTO.DTOConverter sysDeptDTOConverter = new SysDeptDTO.DTOConverter();

	/**
	 * 查询组织树结构
	 * @return 返回list结构的数据
	 */
	@Override
	public List<SysDeptVO> tree() {
		List<SysDept> sysDeptList = sysDeptDao.findAllAsc("id");
		return sysDeptList.stream().map(sysDeptVOConverter :: doForward).collect(Collectors.toList());
	}

	/**
	 * 新增或者编辑组织数据
	 * @param sysDeptDTO 系统组织传输对象
	 */
	@Override
	public void saveOrUpdate(SysDeptDTO sysDeptDTO) {
		SysDept sysDept = sysDeptDTOConverter.doForward(sysDeptDTO);
		// 非顶级组织时，上级部门是否存在
		if (Validator.isNotEmpty(sysDept.getDeptParent())) {
			// 自己不能是自己的上级
			if (sysDept.getDeptParent().equals(sysDept.getId())) {
				throw new BusException(RestError.ERROR2000, "自已不能是自己的上级部门！");
			}
			SysDept deptParent = sysDeptDao.findOne(sysDept.getDeptParent());
			if (deptParent == null) {
				throw new BusException(RestError.ERROR2000, "上级部门不存在，请刷新页面！");
			}
			// 修改时上级部门不能是自己的子部门
			if (Validator.isNotNull(sysDept.getId())) {
				List<SysDept> sysDeptList = TreeUtil.tileTree(sysDeptDao.findChildren(sysDept.getId()), SysDept::getChildren);
				if (sysDeptList.size() > 0) {
					boolean conflict = sysDeptList.stream().anyMatch(dept -> sysDept.getId().equals(dept.getDeptParent()) && sysDept.getDeptParent().equals(dept.getId()));
					if (conflict) {
						throw new BusException(RestError.ERROR2000, "上级部门不能是自已子部门！");
					}
				}
			}
		}
		// 保存数据
		sysDeptDao.saveOrUpdate(sysDept);
	}

	/**
	 * 删除当前组织，这里是物理删除，编辑里能够把其置成失效
	 * @param deptId 组织的id
	 */
	@Override
	public void delete(Integer deptId) {
		// 查询当前组织
		SysDept sysDept = sysDeptDao.findOne(deptId);
		// 把子组织转成列表
		List<SysDept> sysDeptList = TreeUtil.tileTree(Collections.singletonList(sysDept), SysDept::getChildren);
		// 需要失效用户的组织列表
		if (sysDeptList.size() > 0) {
			Set<Integer> idSet = new HashSet<>();
			sysDeptList.forEach(dept -> idSet.add(dept.getId()));
			// 归属当前组织的用户失效
			List<SysUser> sysUserList = sysUserDao.findByDepts(idSet.toArray());
			if (sysUserList != null && sysUserList.size() > 0) {
				// 先更改状态，如果先删除deptId会被置成空，导致查不到用户
				for (SysUser sysUser : sysUserList) {
					sysUser.setIfValid(IfValid.INVALID.getCode());
				}
				sysUserDao.update(sysUserList);
			}
		}
		// 删除组织及子组织
		sysDeptDao.deleteAll(sysDeptList);
	}
}
