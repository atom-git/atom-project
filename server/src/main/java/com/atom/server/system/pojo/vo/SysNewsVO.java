package com.atom.server.system.pojo.vo;

import com.atom.common.dao.Converter;
import com.atom.common.pojo.AbsEntity;
import com.atom.server.system.entity.SysNews;
import com.atom.server.system.entity.SysUser;
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
	@ApiModelProperty("信息类型1通知，2消息，3待办")
	private Integer type;
	@ApiModelProperty("标题")
	private String title;
	@ApiModelProperty("源用户，系统提示用户为空")
	private Integer fromUser;
	@ApiModelProperty("源用户")
	private SysUser fromSysUser;
	@ApiModelProperty("目标用户id")
	private Integer toUser;
	@ApiModelProperty("目标用户")
	private SysUser toSysUser;
	@ApiModelProperty("路由")
	private String route;
	@ApiModelProperty("消息状态：0未读 1已读")
	private Integer status;
	@ApiModelProperty("消息创建时间")
	private Date createTime;
	@ApiModelProperty("是否有效1有效，0失效")
	private Integer ifValid;

	public static class VOConverter extends Converter<SysNewsVO, SysNews> {

		@Override
		public SysNewsVO doForward(SysNews sysNews) {
			if (sysNews == null) {
				return null;
			}
			SysNewsVO sysNewsVO = new SysNewsVO();
			BeanUtils.copyProperties(sysNews, sysNewsVO);
			return sysNewsVO;
		}
	}
}
