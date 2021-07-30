package com.atom.server.system.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * @author zr
 * @description 系统用户角色关系表
 * @date 2021/4/22
 */
@Entity
@Table(name = "sys_user_role")
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor
public class SysUserRole {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "user_id")
	private Integer userId;
	@Column(name = "role_id")
	private Integer roleId;

	@ManyToOne(cascade = CascadeType.DETACH, optional=false)
	@JoinColumn(name = "role_id", insertable = false, updatable = false)
	private SysRole sysRole;

	/**
	 * 用户角色关系构造
	 * @param userId 用户id
	 * @param roleId 角色id
	 */
	public SysUserRole(Integer userId, Integer roleId) {
		this.userId = userId;
		this.roleId = roleId;
	}
}
