package com.atom.server.system.pojo.dto;

import com.atom.common.pojo.AbsEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @author zr
 * @description 系统角色的菜单及资源DTO
 * @date 2020/6/9
 */
@Getter
@Setter
@ApiModel("系统角色的菜单及资源DTO")
public class SysPermissionDTO extends AbsEntity {

	@NotNull
	@ApiModelProperty("选中的菜单")
	private Set<Integer> menusSet;
	@NotNull
	@ApiModelProperty("选中的动作资源")
	private Set<Integer> actionSet;
}
