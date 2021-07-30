package com.atom.server.system.pojo.dto;

import com.atom.common.dao.Converter;
import com.atom.common.pojo.AbsEntity;
import com.atom.server.system.entity.SysRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author zr
 * @description 系统角色编辑DTO
 * @date 2021/4/23
 */
@Getter
@Setter
@ApiModel("系统角色编辑DTO")
public class SysRoleDTO extends AbsEntity {

	@ApiModelProperty("角色ID")
	private Integer id;
	@NotEmpty
	@Length(max = 50)
	@ApiModelProperty("角色名称")
	private String roleName;
	@ApiModelProperty("角色描述")
	private String roleDesc;
	@NotNull
	@ApiModelProperty("是否默认角色1是，0否，最多只允许一个默认权限")
	private Integer ifDefault;

	public static class DTOConverter extends Converter<SysRole, SysRoleDTO> {
		@Override
		public SysRole doForward(SysRoleDTO sysRoleDTO) {
			if (sysRoleDTO == null) {
				return null;
			}
			SysRole sysRole = new SysRole();
			BeanUtils.copyProperties(sysRoleDTO, sysRole);
			return sysRole;
		}
	}
}
