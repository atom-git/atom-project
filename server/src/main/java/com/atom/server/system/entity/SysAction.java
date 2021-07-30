package com.atom.server.system.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * @author zr
 * @description 系统api资源信息，对应菜单按钮
 * @date 2021/4/22
 */
@Entity
@Table(name = "sys_action")
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor
public class SysAction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "name")
	private String name;
	@Column(name = "url")
	private String url;
	@Column(name = "type")
	private Integer type;
	@Column(name = "create_time")
	private Date createTime;
	@Column(name = "topic_id")
	private Integer topicId;
	@Column(name = "topic_name")
	private String topicName;
	@Column(name = "grant_type")
	private Integer grantType;

	/**
	 * 定义构建体
	 */
	public SysAction(String topicName, String name, String url, Integer type, Integer grantType) {
		this.topicName = topicName;
		this.name = name;
		this.url = url;
		this.type = type;
		this.grantType = grantType;
	}
}
