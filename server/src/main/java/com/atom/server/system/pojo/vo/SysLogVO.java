package com.atom.server.system.pojo.vo;

import com.atom.common.dao.Converter;
import com.atom.common.pojo.AbsEntity;
import com.atom.common.pojo.mapper.PlatformType;
import com.atom.server.system.entity.SysLog;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

/**
 * @author zr
 * @description 系统日志响应实体
 * @date 2021/4/22
 */
@Getter
@Setter
@ApiModel(description = "系统日志响应实体")
public class SysLogVO extends AbsEntity {

	@ApiModelProperty("ID")
	private Integer id;
	@ApiModelProperty("用户帐户")
	private String account;
	@ApiModelProperty("用户昵称")
	private String name;
	@ApiModelProperty("日志类型")
	private Integer type;
	@ApiModelProperty("请求时间")
	private Long createTime;
	@ApiModelProperty("请求地址")
	private String requestUrl;
	@ApiModelProperty("请求参数")
	private String requestParams;
	@ApiModelProperty("请求平台")
	private PlatformType platformType;
	@ApiModelProperty("请求状态")
	private Integer resultStatus;
	@ApiModelProperty("执行时长")
	private Long executionTime;
	@ApiModelProperty("异常")
	private String exception;
	@ApiModelProperty("请求结果")
	private String result;

	public static class VOConverter extends Converter<SysLogVO, SysLog> {
		@Override
		public SysLogVO doForward(SysLog sysLog) {
			if (sysLog == null) {
				return null;
			}
			SysLogVO sysLogVO = new SysLogVO();
			BeanUtils.copyProperties(sysLog, sysLogVO, "actionType", "platformType");
			// platformType
			sysLogVO.setPlatformType(PlatformType.match(sysLog.getPlatformType()));
			return sysLogVO;
		}
	}
}
