package com.atom.server.system.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * @author zr
 * @description 系统用户消息
 * @date 2021/4/22
 */
@Entity
@Table(name = "sys_message")
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
public class SysMessage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "topic_id")
	private Integer topicId;
	@Column(name = "domain")
	private String domain;
	@Column(name = "content")
	private String content;
	@Column(name = "msg_json")
	private String msgJson;
	@Column(name = "msg_type")
	private Integer msgType;
	@Column(name = "create_time")
	private Integer createTime;
	@Column(name = "user_id")
	private Integer userId;
}
