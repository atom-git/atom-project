package com.atom.server.system.pojo.vo;

import com.atom.common.dao.Converter;
import com.atom.common.pojo.AbsEntity;
import com.atom.server.system.entity.SysForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * @author zr
 * @description 系统自定义表单响应实体
 * @date 2021/11/22
 */
@Getter
@Setter
@ApiModel(description = "系统自定义表单响应实体")
public class SysFormVO extends AbsEntity {

	@ApiModelProperty("用户ID")
	private Integer id;
	@ApiModelProperty("表单标题")
	private String title;
	@ApiModelProperty("表单布局方式horizontal | vertical")
	private String layout;
	@ApiModelProperty("表单宽度")
	private Integer width;
	@ApiModelProperty("弹窗大小")
	private Integer dialogSize;
	@ApiModelProperty("标签对齐方式")
	private String labelAlign;
	@ApiModelProperty("标签宽度类型")
	private String labelColType;
	@ApiModelProperty("标签宽度")
	private String labelColSize;
	@ApiModelProperty("组件大小")
	private String size;
	@ApiModelProperty("是否显示冒号")
	private String colon;
	@ApiModelProperty("配置内容")
	private String content;
	@ApiModelProperty("修改时间")
	private Date modifyTime;
	@ApiModelProperty("创建人ID")
	private Integer creatorId;
	@ApiModelProperty("创建人名称")
	private String creatorName;
	@ApiModelProperty("状态 是否有效1有效，0失效")
	private Integer ifValid;

	public static class VOConverter extends Converter<SysFormVO, SysForm> {

		@Override
		public SysFormVO doForward(SysForm sysForm) {
			if (sysForm == null) {
				return null;
			}
			SysFormVO sysFormVO = new SysFormVO();
			BeanUtils.copyProperties(sysForm, sysFormVO);
			return sysFormVO;
		}
	}
}
