package com.atom.server.system.pojo.vo;

import com.atom.common.dao.Converter;
import com.atom.common.pojo.AbsEntity;
import com.atom.server.system.entity.SysActionTopic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zr
 * @description 系统动作响应资源主题,一个菜单可以有N个主题
 * @date 2021/4/22
 */
@Getter
@Setter
@ApiModel("系统动作响应资源主题")
public class SysActionTopicVO extends AbsEntity {
	@ApiModelProperty("主题id")
	private Integer id;
	@ApiModelProperty("主题名称")
	private String name;
	@ApiModelProperty("主题包括的资源列表")
	private List<SysActionVO> sysActionList;

	public static class VOConverter extends Converter<SysActionTopicVO, SysActionTopic> {

		private final SysActionVO.VOConverter sysActionVOConverter = new SysActionVO.VOConverter();

		@Override
		public SysActionTopicVO doForward(SysActionTopic sysActionTopic) {
			if (sysActionTopic == null) {
				return null;
			}
			SysActionTopicVO sysActionTopicVO = new SysActionTopicVO();
			BeanUtils.copyProperties(sysActionTopic, sysActionTopicVO);
			if (sysActionTopic.getSysActionList() != null) {
				sysActionTopicVO.setSysActionList(sysActionTopic.getSysActionList().stream().map(sysActionVOConverter :: doForward).collect(Collectors.toList()));
			} else {
				sysActionTopicVO.setSysActionList(new ArrayList<>());
			}
			return sysActionTopicVO;
		}

		/**
		 * 转换成是否选中的VO
		 * @param sysActionTopic 需要转换的动作主题
		 * @param actionSet 选中的动作资源
		 * @return 返回VO
		 */
		public SysActionTopicVO doForward(SysActionTopic sysActionTopic, Set<Integer> actionSet) {
			if (sysActionTopic == null) {
				return null;
			}
			SysActionTopicVO sysActionTopicVO = new SysActionTopicVO();
			BeanUtils.copyProperties(sysActionTopic, sysActionTopicVO);
			if (sysActionTopic.getSysActionList() != null) {
				sysActionTopicVO.setSysActionList(sysActionTopic.getSysActionList().stream().map(sysAction -> sysActionVOConverter.doForward(sysAction, actionSet)).collect(Collectors.toList()));
			} else {
				sysActionTopicVO.setSysActionList(new ArrayList<>());
			}
			return sysActionTopicVO;
		}
	}
}
