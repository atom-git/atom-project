package com.atom.server.system.pojo.dto;

import cn.hutool.core.date.DateUtil;
import com.atom.common.dao.Converter;
import com.atom.common.pojo.AbsEntity;
import com.atom.server.system.entity.SysForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author zr
 * @description 系统自定义表单传输对象DTO
 * @date 2021/11/22
 */
@Getter
@Setter
@ApiModel("系统自定义表单传输对象")
public class SysFormDTO extends AbsEntity {

	@ApiModelProperty("用户ID")
	private Integer id;
	@NotEmpty
	@ApiModelProperty("表单标题")
	private String title;
	@NotNull
	@ApiModelProperty("表单宽度")
	private Integer width;
	@NotEmpty
	@ApiModelProperty("表单配置")
	private String formConfig;
	@NotEmpty
	@ApiModelProperty("配置内容")
	private String widgets;

	public static class DTOConverter extends Converter<SysForm, SysFormDTO> {

		@Override
		public SysForm doForward(SysFormDTO sysFormDTO) {
			if (sysFormDTO == null) {
				return null;
			}
			SysForm sysForm = new SysForm();
			BeanUtils.copyProperties(sysFormDTO, sysForm);
			// 设置更新时间
			sysForm.setModifyTime(DateUtil.date());
			return sysForm;
		}
	}
}
