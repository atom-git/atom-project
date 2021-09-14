package com.atom.server.system.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author zr
 * @description 系统菜单信息
 * @date 2021/4/22
 */
@Entity
@Table(name = "sys_menu")
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
public class SysMenu {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "menu_name")
	private String name;
	@Column(name = "english_name")
	private String englishName;
	@Column(name = "menu_desc")
	private String desc;
	@Column(name = "menu_icon")
	private String icon;
	@Column(name = "route_name")
	private String route;
	@Column(name = "parent_id")
	private Integer parentId;
	@Column(name = "menu_order")
	private Integer menuOrder;
	@Column(name = "modify_time")
	private Date modifyTime;
	@Column(name = "hidden")
	private Integer hidden;
	@Column(name = "if_valid")
	private Integer ifValid;
	@OneToMany(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "menu_id", insertable = false, updatable = false)
	private List<SysMenuTopic> menuTopicList;
}
