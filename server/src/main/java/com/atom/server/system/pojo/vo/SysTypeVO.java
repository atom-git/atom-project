package com.atom.server.system.pojo.vo;

import com.atom.common.dao.Converter;
import com.atom.common.pojo.AbsEntity;
import com.atom.server.system.entity.SysType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

/**
 * @author zr
 * @description 系统数据字典类型VO
 * @date 2021/4/22
 */
@Getter
@Setter
@ApiModel("系统数据字典类型VO")
public class SysTypeVO extends AbsEntity {
	@ApiModelProperty("字典ID")
	private Integer id;
	@ApiModelProperty("字典名称")
	private String meanName;
	@ApiModelProperty("字典描述")
	private String meanDesc;
	@ApiModelProperty("是否多层级1是，0否")
	private Integer multiLevel;

	public static class VOConverter extends Converter<SysTypeVO, SysType> {
		@Override
		public SysTypeVO doForward(SysType sysType) {
			if (sysType == null) {
				return null;
			}
			SysTypeVO sysTypeVO = new SysTypeVO();
			BeanUtils.copyProperties(sysType, sysTypeVO);
			return sysTypeVO;
		}
	}
}
