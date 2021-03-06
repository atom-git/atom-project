package com.atom.server.system.pojo.dto;

import cn.hutool.core.util.ArrayUtil;
import com.atom.common.dao.Converter;
import com.atom.server.system.entity.SysUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

/**
 * @author zr
 * @description 系统用户传输对象DTO
 * @date 2021/4/23
 */
@Getter
@Setter
@ApiModel("系统用户编辑对象")
public class SysUserDTO {
	@ApiModelProperty("用户ID")
	private Integer id;
	@ApiModelProperty("用户帐户")
	private String account;
	@ApiModelProperty("头像Base64")
	private String head;
	@ApiModelProperty("手机号")
	private String phone;
	@ApiModelProperty("邮箱地址")
	private String email;
	@ApiModelProperty("用户昵称")
	private String name;
	@ApiModelProperty("格言")
	private String motto;
	@ApiModelProperty("位置编码")
	private String[] location;
	@ApiModelProperty("位置地址")
	private String[] locationName;
	@ApiModelProperty("组织部门ID")
	private Integer deptId;
	@ApiModelProperty("是否启用 0：禁用 1：启用")
	private Integer ifValid;
	@ApiModelProperty("原密码")
	private String originPassword;
	@ApiModelProperty("密码")
	private String password;

	public static class DTOConverter extends Converter<SysUser, SysUserDTO> {
		@Override
		public SysUser doForward(SysUserDTO sysUserDTO) {
			if (sysUserDTO == null) {
				return null;
			}
			SysUser sysUser = new SysUser();
			BeanUtils.copyProperties(sysUserDTO, sysUser, "location", "locationName");
			sysUser.setLocation(ArrayUtil.join(sysUserDTO.getLocation(), "|"));
			sysUser.setLocationName(ArrayUtil.join(sysUserDTO.getLocationName(), "|"));
			return sysUser;
		}
	}
}
