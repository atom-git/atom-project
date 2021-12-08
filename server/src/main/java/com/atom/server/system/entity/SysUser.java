package com.atom.server.system.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.JoinFormula;

import javax.persistence.*;
import java.util.Date;

/**
 * @author zr
 * @description 系统用户
 * @date 2021/4/22
 */
@Entity
@Table(name = "sys_user")
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
public class SysUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "account")
	private String account;
	@Column(name = "phone")
	private String phone;
	@Column(name = "email")
	private String email;
	@Column(name = "name")
	private String name;
	@Column(name = "password")
	private String password;
	@Column(name = "motto")
	private String motto;
	@Column(name = "nick_name")
	private String nickName;
	@Column(name = "open_id")
	private String openId;
	@Column(name = "location")
	private String location;
	@Column(name = "location_name")
	private String locationName;
	@Column(name = "head")
	private String head;
	@Column(name = "dept_id")
	private Integer deptId;
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "dept_id", insertable = false, updatable = false)
	@JoinFormula(value = "'1'", referencedColumnName = "if_valid")
	private SysDept sysDept;
	@Column(name = "create_time")
	private Date createTime;
	@Column(name = "update_time")
	private Date updateTime;
	@Column(name = "platform")
	private String platform;
	@Column(name = "app_config")
	private String appConfig;
	@Column(name = "extra_info")
	private String extraInfo;
	@Column(name = "if_valid")
	private Integer ifValid;
}
