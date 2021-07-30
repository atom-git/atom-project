package com.atom.server.system.pojo.dto;

import com.atom.common.dao.Converter;
import com.atom.common.pojo.AbsEntity;
import com.atom.common.validation.IsPhone;
import com.atom.server.system.entity.SysDept;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotEmpty;

/**
 * @author zr
 * @description 系统组织DTO对象
 * @date 2021/4/23
 */
@Getter
@Setter
@ApiModel(description = "系统组织DTO对象")
public class SysDeptDTO extends AbsEntity {
	@ApiModelProperty("部门ID")
	private Integer id;
	@NotEmpty
	@ApiModelProperty("部门名称")
	private String deptName;
	@ApiModelProperty("部门描述信息")
	private String deptDesc;
	@ApiModelProperty("部门负责人名称")
	private String leaderName;
	@NotEmpty
	@IsPhone
	@ApiModelProperty("部门负责人电话")
	private String leaderPhone;
	@ApiModelProperty("父级部门ID")
	private Integer deptParent;
	@ApiModelProperty("是否有效")
	private Integer ifValid;
	public static class DTOConverter extends Converter<SysDept, SysDeptDTO> {
		@Override
		public SysDept doForward(SysDeptDTO sysDeptDTO) {
			if (sysDeptDTO == null) {
				return null;
			}
			SysDept sysDept = new SysDept();
			BeanUtils.copyProperties(sysDeptDTO, sysDept);
			return sysDept;
		}
	}
}
