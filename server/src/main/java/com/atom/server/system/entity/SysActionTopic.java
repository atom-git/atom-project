package com.atom.server.system.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

/**
 * @author zr
 * @description 系统动作响应资源对应的主题，一个菜单可以有N个主题
 * @date 2021/4/22
 */
@Entity
@Table(name = "sys_action_topic")
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
public class SysActionTopic {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "name")
	private String name;
	@OneToMany(cascade = CascadeType.DETACH)
	@JoinColumn(name = "topic_id", insertable = false, updatable = false)
	private List<SysAction> sysActionList;

	public SysActionTopic(String name) {
		this.name = name;
	}
}
