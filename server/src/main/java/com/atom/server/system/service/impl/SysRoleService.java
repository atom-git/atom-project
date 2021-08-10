package com.atom.server.system.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Validator;
import com.atom.common.pojo.exception.BusException;
import com.atom.common.pojo.http.RestError;
import com.atom.server.system.dao.*;
import com.atom.server.system.entity.*;
import com.atom.server.system.pojo.dto.SysPermissionDTO;
import com.atom.server.system.pojo.dto.SysRoleDTO;
import com.atom.server.system.pojo.vo.*;
import com.atom.server.system.service.ISysRoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zr
 * @description 系统角色管理服务
 * @date 2021/4/23
 */
@Service
@Transactional
public class SysRoleService implements ISysRoleService {

	/**
	 * 角色dao
	 */
	@Resource
	private ISysRoleDao sysRoleDao;

	/**
	 * 菜单dao
	 */
	@Resource
	private ISysMenuDao sysMenuDao;

	/**
	 * 资源dao
	 */
	@Resource
	private ISysActionDao sysActionDao;

	/**
	 * 角色菜单dao
	 */
	@Resource
	private ISysRoleMenuDao sysRoleMenuDao;

	/**
	 * 角色资源dao
	 */
	@Resource
	private ISysRoleActionDao sysRoleActionDao;

	/**
	 * 菜单动作主题dao
	 */
	@Resource
	private ISysMenuTopicDao sysMenuTopicDao;

	/**
	 * 动作主题dao
	 */
	@Resource
	private ISysActionTopicDao sysActionTopicDao;

	/**
	 * 系统角色VO转换器
	 */
	private final SysRoleVO.VOConverter sysRoleVOConverter = new SysRoleVO.VOConverter();

	/**
	 * 系统菜单VO转换器
	 */
	private final SysMenuVO.VOConverter sysMenuVOConverter = new SysMenuVO.VOConverter();

	/**
	 * 系统角色DTO转换器
	 */
	private final SysRoleDTO.DTOConverter sysRoleDTOConverter = new SysRoleDTO.DTOConverter();

	/**
	 * 系统资源VO转换器
	 */
	private final SysActionTopicVO.VOConverter sysActionTopicVOConverter = new SysActionTopicVO.VOConverter();

	/**
	 * 系统角色菜单资源VO转换器
	 */
	private final SysRoleMenuVO.VOConverter sysRoleMenuVOConverter = new SysRoleMenuVO.VOConverter();

	/**
	 * 获取系统角色数据表，展示为左侧是角色树，右侧为菜单与资源权限配置
	 * @return 返回当前页的角色数据列表
	 */
	@Override
	public List<SysRoleVO> list() {
		// 查询全部角色列表
		List<SysRole> sysRoleList = sysRoleDao.findAll();
		return sysRoleList.stream().map(sysRoleVOConverter :: doForward).collect(Collectors.toList());
	}

	/**
	 * 新增或者编辑角色信息
	 * @param sysRoleDTO 角色传输dto
	 */
	@Override
	public void saveOrUpdate(SysRoleDTO sysRoleDTO) {
		SysRole sysRole = sysRoleDTOConverter.doForward(sysRoleDTO);
		// 判断角色名称是否有重复
		if (sysRoleDao.ifNameDuplicate(sysRole)) {
			throw new BusException(RestError.ERROR2000, "角色名称不能重复");
		}
		if (Validator.isNotNull(sysRole.getId())) {
			// 角色是否存在
			SysRole originRole = sysRoleDao.findOne(sysRole.getId());
			if (Validator.isNull(originRole)) {
				throw new BusException(RestError.ERROR2000, "修改的角色不存在");
			}
			originRole.setModifyTime(DateUtil.date());
			BeanUtils.copyProperties(sysRoleDTO, originRole);
			sysRoleDao.update(originRole);
		} else {
			sysRole.setModifyTime(DateUtil.date());
			sysRoleDao.save(sysRole);
		}
	}

	/**
	 * 删除系统角色
	 * @param roleId 角色id
	 */
	@Override
	public void delete(Integer roleId) {
		// 查询角色
		SysRole sysRole = sysRoleDao.findOne(roleId);
		if (Validator.isNull(sysRole)) {
			throw new BusException(RestError.ERROR2000, "角色不存在");
		}
		sysRoleDao.delete(sysRole);
	}

	/**
	 * 查询角色权限
	 * @param roleId 角色id
	 * @return 返回角色权限
	 */
	@Override
	public SysRoleMenuVO permission(Integer roleId) {
		// 查询角色
		SysRole sysRole = sysRoleDao.findOne(roleId);
		if (Validator.isNull(sysRole)) {
			throw new BusException(RestError.ERROR2000, "角色不存在");
		}
		// 查询有效的菜单列表
		List<SysMenu> sysMenuList = sysMenuDao.findAll();
		// 转换为SysMenuVO列表
		List<SysMenuVO> sysMenuVOList = sysMenuList.stream().map(sysMenuVOConverter::doForward).collect(Collectors.toList());
		// 查询角色拥有的菜单列表
		List<SysRoleMenu> sysRoleMenuList = sysRoleMenuDao.findAllByField("roleId", roleId);
		// 转变成选中的menu的列表
		Set<Integer> menuSet = new HashSet<>();
		sysRoleMenuList.forEach(sysRoleMenu -> menuSet.add(sysRoleMenu.getMenuId()));
		// 查询所有动作列表
		List<SysActionTopic> sysActionTopicList = sysActionTopicDao.findAll();
		// 查询角色拥有的动作列表
		List<SysRoleAction> sysRoleActionList = sysRoleActionDao.findAllByField("roleId", roleId);
		// 转变成选中的action的列表
		Set<Integer> actionSet = new HashSet<>();
		sysRoleActionList.forEach(sysRoleAction -> actionSet.add(sysRoleAction.getActionId()));
		// 生成动作主题VO
		List<SysActionTopicVO> sysActionTopicVOList = sysActionTopicList.stream()
				.map(sysActionTopic -> sysActionTopicVOConverter.doForward(sysActionTopic, actionSet))
				.collect(Collectors.toList());
		return sysRoleMenuVOConverter.doForward(sysRole, menuSet, sysMenuVOList, actionSet, sysActionTopicVOList);
	}

	/**
	 * 给角色赋权
	 * @param roleId 角色id
	 * @param sysPermissionDTO 角色权限信息
	 */
	@Override
	public void updatePermission(Integer roleId, SysPermissionDTO sysPermissionDTO) {
		// 查询角色
		SysRole sysRole = sysRoleDao.findOne(roleId);
		if (Validator.isNull(sysRole)) {
			throw new BusException(RestError.ERROR9000, "角色不存在");
		}
		// 删除原有角色权限
		List<SysRoleMenu> sysRoleMenuList = sysRoleMenuDao.findAllByField("roleId", roleId);
		if (sysRoleMenuList != null && sysRoleMenuList.size() > 0) {
			sysRoleMenuDao.deleteAll(sysRoleMenuList);
		}
		// 查询有效的菜单列表
		List<SysMenu> sysMenuList = sysMenuDao.findValidByIds(sysPermissionDTO.getMenusSet());
		// 写入新的角色菜单权限列表
		if (sysMenuList != null && sysMenuList.size() > 0) {
			sysRoleMenuList = sysMenuList.stream().map(sysMenu -> new SysRoleMenu(roleId, sysMenu.getId())).collect(Collectors.toList());
			sysRoleMenuDao.save(sysRoleMenuList);
		}
		// 删除原有角色资源列表
		List<SysRoleAction> sysRoleActionList = sysRoleActionDao.findAllByField("roleId", roleId);
		if (sysRoleActionList != null && sysRoleActionList.size() > 0) {
			sysRoleActionDao.deleteAll(sysRoleActionList);
		}
		// 查询有效的资源权限列表
		// 写入新的角色资源权限列表
		List<SysAction> sysActionList = sysActionDao.findByIds(sysPermissionDTO.getActionSet());
		if (sysActionList != null && sysActionList.size() > 0) {
			sysRoleActionList = sysActionList.stream().map(sysAction -> new SysRoleAction(roleId, sysAction.getId())).collect(Collectors.toList());
			sysRoleActionDao.save(sysRoleActionList);
		}
	}

}
