package com.atom.server.system.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * @author zr
 * @description 系统角色菜单关系表
 * @date 2021/4/22
 */
@Entity
@Table(name = "sys_role_menu")
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor
public class SysRoleMenu {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "role_id")
	private Integer roleId;
	@Column(name = "menu_id")
	private Integer menuId;

	public SysRoleMenu(Integer roleId, Integer menuId) {
		this.roleId = roleId;
		this.menuId = menuId;
	}
}
