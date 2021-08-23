package com.atom.server.system.pojo.vo;

import com.atom.common.dao.Converter;
import com.atom.common.pojo.AbsEntity;
import com.atom.server.system.entity.SysNews;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zr
 * @description 用户系统提醒消息待办响应VO
 * @date 2021/8/23
 */
@Getter
@Setter
@ApiModel("用户系统提醒消息待办响应VO")
public class UserNewsVO extends AbsEntity {
	@ApiModelProperty("提醒列表")
	private List<SysNewsVO> noticeList;
	@ApiModelProperty("消息列表")
	private List<SysNewsVO> messageList;
	@ApiModelProperty("待办列表")
	private List<SysNewsVO> todoList;

	public static class VOConverter extends Converter<SysNewsVO, SysNews> {

		/**
		 * 系统通知消息待办VO转换器
		 */
		private final SysNewsVO.VOConverter sysNewsVOConverter = new SysNewsVO.VOConverter();

		public UserNewsVO doForward(List<SysNews> noticeList, List<SysNews> messageList, List<SysNews> todoList) {
			UserNewsVO userNewsVO = new UserNewsVO();
			userNewsVO.setNoticeList(noticeList.stream().map(sysNewsVOConverter::doForward).collect(Collectors.toList()));
			userNewsVO.setMessageList(messageList.stream().map(sysNewsVOConverter::doForward).collect(Collectors.toList()));
			userNewsVO.setTodoList(todoList.stream().map(sysNewsVOConverter::doForward).collect(Collectors.toList()));
			return userNewsVO;
		}
	}
}
