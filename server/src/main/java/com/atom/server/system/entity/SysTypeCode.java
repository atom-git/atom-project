package com.atom.server.system.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * @author zr
 * @description 系统码表
 * @date 2021/4/22
 */
@Entity
@Table(name = "sys_type_code")
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
public class SysTypeCode {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "type_name")
	private String typeName;
	@Column(name = "type_desc")
	private String typeDesc;
	@Column(name = "parent_id")
	private Integer parentId;
	@Column(name = "type_order")
	private Integer typeOrder;
	@Column(name = "mean_id")
	private Integer meanId;
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "mean_id", insertable = false, updatable = false)
	private SysType sysType;
}
