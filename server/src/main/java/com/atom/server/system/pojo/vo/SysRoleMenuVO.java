package com.atom.server.system.pojo.vo;

import com.atom.common.dao.Converter;
import com.atom.common.pojo.AbsEntity;
import com.atom.server.system.entity.SysRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

/**
 * @author zr
 * @description 系统角色的菜单及资源权限VO
 * @date 2021/4/22
 */
@Getter
@Setter
@ApiModel("系统角色的菜单及资源权限VO")
public class SysRoleMenuVO extends AbsEntity {

	@ApiModelProperty("角色ID")
	private Integer id;
	@ApiModelProperty("角色名称")
	private String roleName;
	@ApiModelProperty("选中的菜单keys")
	private Set<Integer> checkedMenus;
	@ApiModelProperty("权限的菜单及资源VO")
	private List<SysMenuActionVO> sysMenuActionList;

	public static class VOConverter extends Converter<SysRoleMenuVO, SysRole> {

		public SysRoleMenuVO doForward(SysRole sysRole, List<SysMenuActionVO> sysMenuActionVOList, Set<Integer> menuSet) {
			SysRoleMenuVO sysRoleMenuVO = new SysRoleMenuVO();
			sysRoleMenuVO.setId(sysRole.getId());
			sysRoleMenuVO.setRoleName(sysRole.getRoleName());
			sysRoleMenuVO.setCheckedMenus(menuSet);
			sysRoleMenuVO.setSysMenuActionList(sysMenuActionVOList);
			return sysRoleMenuVO;
		}
	}
}
