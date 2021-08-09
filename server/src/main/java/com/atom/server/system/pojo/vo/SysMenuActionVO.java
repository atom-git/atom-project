package com.atom.server.system.pojo.vo;

import com.atom.common.dao.Converter;
import com.atom.common.pojo.AbsEntity;
import com.atom.server.system.entity.SysMenu;
import com.atom.server.system.entity.SysMenuTopic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zr
 * @description 系统菜单的资源权限VO
 * @date 2021/4/22
 */
@Getter
@Setter
@ApiModel("系统菜单的资源权限VO")
public class SysMenuActionVO extends AbsEntity {

	@ApiModelProperty("菜单ID")
	private Integer id;
	@ApiModelProperty("菜单名称")
	private String name;
	@ApiModelProperty("父级菜单ID")
	private Integer parentId;
	@ApiModelProperty("按照主题归类动作列表")
	private List<SysActionTopicVO> sysActionTopicList;

	public static class VOConverter extends Converter<SysMenuActionVO, SysMenu> {

		public SysMenuActionVO doForward(SysMenu sysMenu, List<SysMenuTopic> sysMenuTopicList, List<SysActionTopicVO> sysActionTopicVOList) {
			SysMenuActionVO sysMenuActionVO = new SysMenuActionVO();
			sysMenuActionVO.setId(sysMenu.getId());
			sysMenuActionVO.setName(sysMenu.getName());
			sysMenuActionVO.setParentId(sysMenu.getParentId());
			if (sysMenuTopicList != null && sysMenuTopicList.size() > 0) {
				List<Integer> topicList = sysMenuTopicList.stream()
						.filter(sysMenuTopic -> sysMenuTopic.getMenuId() == sysMenu.getId())
						.map(SysMenuTopic::getTopicId).collect(Collectors.toList());
				if (sysActionTopicVOList != null && sysActionTopicVOList.size() > 0) {
					sysMenuActionVO.setSysActionTopicList(sysActionTopicVOList.stream()
							.filter(sysActionTopicVO -> topicList.contains(sysActionTopicVO.getId())).collect(Collectors.toList()));
				} else {
					sysMenuActionVO.setSysActionTopicList(new ArrayList<>());
				}
			} else {
				sysMenuActionVO.setSysActionTopicList(new ArrayList<>());
			}
			return sysMenuActionVO;
		}
	}
}
