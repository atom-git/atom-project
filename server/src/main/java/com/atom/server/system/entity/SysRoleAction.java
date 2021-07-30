package com.atom.server.system.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * @author zr
 * @description 系统角色资源关系表
 * @date 2021/4/22
 */
@Entity
@Table(name = "sys_role_action")
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor
public class SysRoleAction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "role_id")
	private Integer roleId;
	@Column(name = "action_id")
	private Integer actionId;

	public SysRoleAction(Integer roleId, Integer actionId) {
		this.roleId = roleId;
		this.actionId = actionId;
	}
}
