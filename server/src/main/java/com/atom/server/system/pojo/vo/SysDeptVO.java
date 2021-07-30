package com.atom.server.system.pojo.vo;

import com.atom.common.dao.Converter;
import com.atom.common.pojo.AbsEntity;
import com.atom.server.system.entity.SysDept;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

/**
 * @author zr
 * @description 系统组织响应实体
 * @date 2021/4/22
 */
@Getter
@Setter
@ApiModel(description = "系统组织响应实体")
public class SysDeptVO extends AbsEntity {

	@ApiModelProperty("部门ID")
	private Integer id;
	@ApiModelProperty("部门名称")
	private String deptName;
	@ApiModelProperty("部门描述信息")
	private String deptDesc;
	@ApiModelProperty("部门负责人名称")
	private String leaderName;
	@ApiModelProperty("部门负责人电话")
	private String leaderPhone;
	@ApiModelProperty("父级部门ID")
	private Integer deptParent;
	@ApiModelProperty("是否启用 0：禁用 1：启用")
	private Integer ifValid;

	public static class VOConverter extends Converter<SysDeptVO, SysDept> {

		@Override
		public SysDeptVO doForward(SysDept sysDept) {
			if (sysDept == null) {
				return null;
			}
			SysDeptVO sysDeptVO = new SysDeptVO();
			BeanUtils.copyProperties(sysDept, sysDeptVO);
			return sysDeptVO;
		}
	}
}
