package com.atom.server.system.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * @author zr
 * @description 系统菜单对应的数据主题
 * @date 2021/4/22
 */
@Entity
@Table(name = "sys_menu_topic")
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
public class SysMenuTopic {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "menu_id")
	private int menuId;
	@Column(name = "topic_id")
	private int topicId;
}
