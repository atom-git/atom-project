package com.atom.server.system.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * @author zr
 * @description 系统角色信息表
 * @date 2021/4/22
 */
@Entity
@Table(name = "sys_role")
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
public class SysRole {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "role_name")
	private String roleName;
	@Column(name = "role_desc")
	private String roleDesc;
	@Column(name = "modify_time")
	private Date modifyTime;
	@Column(name = "if_default")
	private Integer ifDefault;
}
