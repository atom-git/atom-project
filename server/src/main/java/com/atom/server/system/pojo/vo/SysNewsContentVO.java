package com.atom.server.system.pojo.vo;

import com.atom.common.dao.Converter;
import com.atom.common.pojo.AbsEntity;
import com.atom.server.system.entity.SysNewsContent;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

/**
 * @author zr
 * @description 系统提醒消息待办信息VO
 * @date 2021/8/23
 */
@Getter
@Setter
@ApiModel("系统提醒消息待办信息响应实体")
public class SysNewsContentVO extends AbsEntity {

	@ApiModelProperty("主键id")
	private Integer id;
	@ApiModelProperty("消息ID")
	private Integer newsId;
	@ApiModelProperty("内容")
	private String content;
	@ApiModelProperty("附件文件，逗号分隔")
	private String files;
	@ApiModelProperty("扩展信息，传递参数")
	private String extraInfo;

	public static class VOConverter extends Converter<SysNewsContentVO, SysNewsContent> {

		@Override
		public SysNewsContentVO doForward(SysNewsContent sysNewsContent) {
			if (sysNewsContent == null) {
				return null;
			}
			SysNewsContentVO sysNewsContentVO = new SysNewsContentVO();
			BeanUtils.copyProperties(sysNewsContent, sysNewsContentVO);
			// TODO 附件内容转成链接
			return sysNewsContentVO;
		}
	}
}
