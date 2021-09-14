package com.atom.server.system.pojo.dto;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Validator;
import com.atom.common.dao.Converter;
import com.atom.common.pojo.AbsEntity;
import com.atom.server.system.entity.SysMenu;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @author zr
 * @description 系统菜单传输DTO
 * @date 2021/4/23
 */
@Getter
@Setter
@ApiModel("系统菜单传输DTO")
public class SysMenuDTO extends AbsEntity {

	@ApiModelProperty("菜单ID")
	private Integer id;
	@NotEmpty
	@Length(max = 20)
	@ApiModelProperty("菜单名称")
	private String name;
	@NotEmpty
	@ApiModelProperty("英文名称")
	private String englishName;
	@ApiModelProperty("菜单描述")
	private String desc;
	@ApiModelProperty("菜单图标")
	private String icon;
	@NotEmpty
	@ApiModelProperty("菜单路由名称")
	private String route;
	@NotNull
	@ApiModelProperty("父级菜单ID")
	private Integer parentId;
	@NotNull
	@ApiModelProperty("菜单展示优先级")
	private Integer menuOrder;
	@ApiModelProperty("菜单修改时间")
	private Date modifyTime;
	@ApiModelProperty("是否禁用菜单 0：禁用 1：启用")
	private Integer ifValid;
	@NotNull
	@ApiModelProperty("是否隐藏，1是，0否，默认0")
	private Integer hidden;
	@NotNull
	@ApiModelProperty("菜单所对应的动作资源主题列表")
	private List<Integer> topicList;

	public static class DTOConverter extends Converter<SysMenu, SysMenuDTO> {

		@Override
		public SysMenu doForward(SysMenuDTO sysMenuDTO) {
			if (sysMenuDTO == null) {
				return null;
			}
			SysMenu sysMenu = new SysMenu();
			BeanUtils.copyProperties(sysMenuDTO, sysMenu);
			sysMenu.setModifyTime(DateUtil.date());
			return sysMenu;
		}
	}
}
