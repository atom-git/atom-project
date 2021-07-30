package com.atom.server.system.pojo.vo;

import com.atom.common.dao.Converter;
import com.atom.common.pojo.AbsEntity;
import com.atom.server.system.entity.SysMenu;
import com.atom.server.system.entity.SysMenuTopic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zr
 * @description 系统菜单响应实体
 * @date 2021/4/22
 */
@Getter
@Setter
@ApiModel("系统菜单响应实体")
public class SysMenuVO extends AbsEntity {

	@ApiModelProperty("菜单ID")
	private Integer id;
	@ApiModelProperty("菜单名称")
	private String name;
	@ApiModelProperty("菜单描述")
	private String desc;
	@ApiModelProperty("菜单图标")
	private String icon;
	@ApiModelProperty("菜单路由名称")
	private String route;
	@ApiModelProperty("父级菜单ID")
	private Integer parentId;
	@ApiModelProperty("菜单展示优先级")
	private Integer menuOrder;
	@Column(name = "modify_time")
	private Date modifyTime;
	@ApiModelProperty("是否隐藏，1是，0否，默认0")
	private Integer hidden;
	@ApiModelProperty("是否禁用菜单 0：禁用 1：启用")
	private Integer ifValid;
	@ApiModelProperty("子菜单")
	private List<SysMenuVO> children;
	@ApiModelProperty("菜单所对应的动作资源主题列表")
	private List<Integer> topicList;

	/**
	 * 与DB实体的转化类
	 */
	public static class VOConverter extends Converter<SysMenuVO, SysMenu> {

		@Override
		public SysMenuVO doForward(SysMenu sysMenu) {
			SysMenuVO sysMenuVO = new SysMenuVO();
			BeanUtils.copyProperties(sysMenu, sysMenuVO);
			List<SysMenuTopic> menuTopicList = sysMenu.getMenuTopicList();
			List<Integer> topicList = new ArrayList<>();
			if (menuTopicList != null && menuTopicList.size() > 0) {
				menuTopicList.forEach(menuTopic -> {
					topicList.add(menuTopic.getTopicId());
				});
			}
			sysMenuVO.setTopicList(topicList);
			return sysMenuVO;
		}
	}
}
