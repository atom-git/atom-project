package com.atom.server.system.pojo.vo;

import com.atom.common.dao.Converter;
import com.atom.common.pojo.AbsEntity;
import com.atom.server.system.entity.SysRole;
import com.atom.server.system.entity.SysUser;
import com.atom.server.system.entity.SysUserRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zr
 * @description 系统用户角色响应实体
 * @date 2021/4/22
 */
@Getter
@Setter
@ApiModel(description = "系统用户角色响应实体")
public class SysUserRoleVO extends AbsEntity {

	@ApiModelProperty("用户ID")
	private Integer id;
	@ApiModelProperty("系统角色列表")
	private List<SysRoleVO> sysRoleList;
	@ApiModelProperty("用户角色id列表")
	private List<Integer> userRoleList;

	public static class VOConverter extends Converter<SysUserRoleVO, SysUser> {

		private final SysRoleVO.VOConverter sysRoleVOConverter = new SysRoleVO.VOConverter();

		public SysUserRoleVO doForward(SysUser sysUser, List<SysRole> sysRoleList, List<SysUserRole> sysUserRoleList) {
			if (sysUser == null) {
				return null;
			}
			SysUserRoleVO sysUserRoleVO = new SysUserRoleVO();
			sysUserRoleVO.setId(sysUser.getId());
			if (sysRoleList == null || sysRoleList.size() <= 0) {
				sysUserRoleVO.setSysRoleList(new ArrayList<>());
			} else {
				sysUserRoleVO.setSysRoleList(sysRoleList.stream().map(sysRoleVOConverter :: doForward).collect(Collectors.toList()));
			}
			if (sysUserRoleList == null || sysUserRoleList.size() <= 0) {
				sysUserRoleVO.setUserRoleList(new ArrayList<>());
			} else {
				sysUserRoleVO.setUserRoleList(sysUserRoleList.stream().map(SysUserRole::getRoleId).collect(Collectors.toList()));
		}
			return sysUserRoleVO;
		}
	}
}
