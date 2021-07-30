package com.atom.server.system.pojo.dto;

import com.atom.common.dao.Converter;
import com.atom.common.pojo.AbsEntity;
import com.atom.server.system.entity.SysTypeCode;
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
 * @description 系统数据字典DTO
 * @date 2020/6/24
 */
@Getter
@Setter
@ApiModel("系统数据字典DTO")
public class SysTypeCodeDTO extends AbsEntity {
	@ApiModelProperty("字典ID")
	private Integer id;
	@NotEmpty
	@Length(max = 50)
	@ApiModelProperty("类型名称")
	private String typeName;
	@ApiModelProperty("类型描述")
	private String typeDesc;
	@ApiModelProperty("类型父级编码，可以为空")
	private Integer parentId;
	@NotNull
	@ApiModelProperty("字典编号ID")
	private Integer meanId;

	public static class DTOConverter extends Converter<SysTypeCode, SysTypeCodeDTO> {
		@Override
		public SysTypeCode doForward(SysTypeCodeDTO sysTypeCodeDTO) {
			if (sysTypeCodeDTO == null) {
				return null;
			}
			SysTypeCode sysTypeCode = new SysTypeCode();
			BeanUtils.copyProperties(sysTypeCodeDTO, sysTypeCode);
			return sysTypeCode;
		}
	}
}
