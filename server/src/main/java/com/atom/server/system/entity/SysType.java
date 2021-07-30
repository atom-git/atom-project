package com.atom.server.system.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * @author zr
 * @description 系统数据字典类型表
 * @date 2021/4/22
 */
@Entity
@Table(name = "sys_type")
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
public class SysType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "mean_name")
	private String meanName;
	@Column(name = "mean_desc")
	private String meanDesc;
	@Column(name = "multi_level")
	private Integer multiLevel;
}
