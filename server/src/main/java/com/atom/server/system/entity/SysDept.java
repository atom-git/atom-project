package com.atom.server.system.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.JoinFormula;

import javax.persistence.*;
import java.util.List;

/**
 * @author zr
 * @description 系统组织机构表
 * @date 2021/4/22
 */
@Entity
@Table(name = "sys_dept")
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
public class SysDept {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "dept_name")
	private String deptName;
	@Column(name = "dept_desc")
	private String deptDesc;
	@Column(name = "leader_name")
	private String leaderName;
	@Column(name = "leader_phone")
	private String leaderPhone;
	@Column(name = "dept_parent")
	private Integer deptParent;
	@Column(name = "if_valid")
	private Integer ifValid;

	@OneToMany(cascade = CascadeType.DETACH)
	@JoinColumn(name = "dept_parent", insertable = false, updatable = false)
	@JoinFormula(referencedColumnName = "if_valid", value = "1")
	private List<SysDept> children;
}

