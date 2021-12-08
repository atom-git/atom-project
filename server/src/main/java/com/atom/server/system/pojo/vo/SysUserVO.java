package com.atom.server.system.pojo.vo;

import com.atom.common.dao.Converter;
import com.atom.common.pojo.AbsEntity;
import com.atom.server.system.entity.SysUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * @author zr
 * @description 系统用户响应实体
 * @date 2021/4/22
 */
@Getter
@Setter
@ApiModel(description = "系统用户响应实体")
public class SysUserVO extends AbsEntity {

	@ApiModelProperty("用户ID")
	private Integer id;
	@ApiModelProperty("帐户")
	private String account;
	@ApiModelProperty("手机号")
	private String phone;
	@ApiModelProperty("邮箱地址")
	private String email;
	@ApiModelProperty("昵称")
	private String name;
	@ApiModelProperty("格言")
	private String motto;
	@ApiModelProperty("位置编码")
	private String location;
	@ApiModelProperty("位置地址")
	private String locationName;
	@ApiModelProperty("头像")
	private String head;
	@ApiModelProperty("组织机构代码")
	private Integer deptId;
	@ApiModelProperty("用户组织")
	private SysDeptVO sysDept;
	@ApiModelProperty("创建时间")
	private Date createTime;
	@ApiModelProperty("状态 是否有效1有效，0失效")
	private Integer ifValid;

	public static class VOConverter extends Converter<SysUserVO, SysUser> {

		private final SysDeptVO.VOConverter sysDeptVOConverter = new SysDeptVO.VOConverter();

		@Override
		public SysUserVO doForward(SysUser sysUser) {
			if (sysUser == null) {
				return null;
			}
			SysUserVO sysUserVO = new SysUserVO();
			BeanUtils.copyProperties(sysUser, sysUserVO, "sysDept", "location");
			sysUserVO.setSysDept(sysDeptVOConverter.doForward(sysUser.getSysDept()));
			// TODO 把head由key转成图片地址
			return sysUserVO;
		}
	}
}
