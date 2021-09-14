package com.atom.server.system.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zr
 * @description APP配置信息DTO
 * @date 2021/8/18
 */
@Getter
@Setter
@ApiModel("APP配置信息DTO")
public class AppConfigDTO {

	@ApiModelProperty("主题")
	private String theme;
	@ApiModelProperty("主题色")
	private String primaryColor;
	@ApiModelProperty("布局")
	private String layout;
	@ApiModelProperty("固定头部")
	private boolean fixHeader;
	@ApiModelProperty("多标签")
	private boolean multiTab;
	@ApiModelProperty("多标签是否拖拽")
	private boolean multiTabDraggable;
	@ApiModelProperty("弹窗样式")
	private Dialog dialog;
	@ApiModelProperty("水印开关")
	private boolean waterMark;
	@ApiModelProperty("切换动画")
	private Transition transition;

	/**
	 * 弹窗配置
	 */
	@Getter
	@Setter
	static class Dialog {
		@ApiModelProperty("弹窗类型")
		private String type;
		@ApiModelProperty("弹窗大小")
		private Integer size;
	}

	/**
	 * 动画配置
	 */
	@Getter
	@Setter
	static class Transition {
		@ApiModelProperty("动画类型")
		private String name;
		@ApiModelProperty("动画方向")
		private String direction;
		@ApiModelProperty("是否开启")
		private boolean disabled;
	}
}
