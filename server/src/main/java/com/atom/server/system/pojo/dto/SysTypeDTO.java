package com.atom.server.system.pojo.dto;

import com.atom.common.dao.Converter;
import com.atom.common.pojo.AbsEntity;
import com.atom.server.system.entity.SysType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotEmpty;

/**
 * @author zr
 * @description 系统数据字典类型DTO
 * @date 2020/6/23
 */
@Getter
@Setter
@ApiModel("系统数据字典类型DTO")
public class SysTypeDTO extends AbsEntity {
	@ApiModelProperty("字典ID")
	private Integer id;
	@NotEmpty
	@ApiModelProperty("字典名称")
	private String meanName;
	@NotEmpty
	@ApiModelProperty("字典描述")
	private String meanDesc;
	@ApiModelProperty("是否多层级1是，0否")
	private Integer multiLevel;

	public static class DTOConverter extends Converter<SysType, SysTypeDTO> {
		@Override
		public SysType doForward(SysTypeDTO sysTypeDTO) {
			if (sysTypeDTO == null) {
				return null;
			}
			SysType sysType = new SysType();
			BeanUtils.copyProperties(sysTypeDTO, sysType);
			return sysType;
		}
	}
}
