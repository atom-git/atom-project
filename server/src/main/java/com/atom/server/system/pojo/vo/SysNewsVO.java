package com.atom.server.system.pojo.vo;

import cn.hutool.core.lang.Validator;
import com.atom.common.dao.Converter;
import com.atom.common.pojo.AbsEntity;
import com.atom.server.system.entity.SysNews;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * @author zr
 * @description 系统提醒消息待办响应VO
 * @date 2021/8/23
 */
@Getter
@Setter
@ApiModel("系统提醒消息待办响应实体")
public class SysNewsVO extends AbsEntity {

	@ApiModelProperty("消息ID")
	private Integer id;
	@ApiModelProperty("信息类型1待办，2通知，3消息")
	private Integer type;
	@ApiModelProperty("标题")
	private String title;
	@ApiModelProperty("源用户，系统提示用户为空")
	private Integer fromUser;
	@ApiModelProperty("源用户")
	private User fromSysUser;
	@ApiModelProperty("目标用户id")
	private Integer toUser;
	@ApiModelProperty("目标用户")
	private User toSysUser;
	@ApiModelProperty("路由")
	private String route;
	@ApiModelProperty("消息状态：0未读 1已读")
	private Integer status;
	@ApiModelProperty("消息创建时间")
	private Date createTime;
	@ApiModelProperty("是否有效1有效，0失效")
	private Integer ifValid;

	/**
	 * 消息来源或者目标用户
	 */
	@Getter
	@Setter
	static class User extends AbsEntity {
		@ApiModelProperty("用户ID")
		private Integer id;
		@ApiModelProperty("帐户")
		private String account;
		@ApiModelProperty("手机号")
		private String phone;
		@ApiModelProperty("昵称")
		private String name;
		@ApiModelProperty("头像")
		private String head;
	}

	public static class VOConverter extends Converter<SysNewsVO, SysNews> {

		@Override
		public SysNewsVO doForward(SysNews sysNews) {
			if (sysNews == null) {
				return null;
			}
			// 转换用户信息
			SysNewsVO sysNewsVO = new SysNewsVO();
			// 转换来源人
			User fromUser;
			if (Validator.isNotNull(sysNews.getFromSysUser())) {
				fromUser = new User();
				BeanUtils.copyProperties(sysNews.getFromSysUser(), fromUser, "sysDept", "appConfig");
			} else {
				fromUser = null;
			}
			// 转换目标人
			User toUser;
			if (Validator.isNotNull(sysNews.getToSysUser())) {
				toUser = new User();
				BeanUtils.copyProperties(sysNews.getToSysUser(), toUser, "sysDept", "appConfig");
			} else {
				toUser = null;
			}
			BeanUtils.copyProperties(sysNews, sysNewsVO, "fromSysUser", "toSysUser");
			sysNewsVO.setFromSysUser(fromUser);
			sysNewsVO.setToSysUser(toUser);
			return sysNewsVO;
		}
	}
}
