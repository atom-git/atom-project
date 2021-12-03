package com.atom.server.system.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * @author zr
 * @description 系统提醒消息待办信息
 * @date 2021/8/23
 */
@Entity
@Table(name = "sys_news")
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
public class SysNews {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "type")
	private Integer type;
	@Column(name = "title")
	private String title;
	@Column(name = "desc")
	private String desc;
	@Column(name = "from_user")
	private Integer fromUser;
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "from_user", insertable = false, updatable = false)
	private SysUser fromSysUser;
	@Column(name = "to_user")
	private Integer toUser;
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "to_user", insertable = false, updatable = false)
	private SysUser toSysUser;
	@Column(name = "route")
	private String route;
	@Column(name = "status")
	private Integer status;
	@Column(name = "create_time")
	private Date createTime;
	@Column(name = "if_valid")
	private Integer ifValid;
}
