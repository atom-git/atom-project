package com.atom.server.system.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * @author zr
 * @description 系统用户消息主题
 * @date 2021/4/22
 */
@Entity
@Table(name = "sys_message_topic")
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
public class SysMessageTopic {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "type")
	private String type;
	@Column(name = "avatar")
	private String avatar;
	@Column(name = "color")
	private String color;
	@Column(name = "title")
	private String title;
	@Column(name = "route")
	private String route;
	@Column(name = "user_id")
	private Integer userId;
	@Column(name = "read_flag")
	private Integer readFlag;
	@Column(name = "create_time")
	private Date createTime;
}
