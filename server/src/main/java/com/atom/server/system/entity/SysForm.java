package com.atom.server.system.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * @author zr
 * @description 系统自定义表单
 * @date 2021/11/18
 */
@Entity
@Table(name = "sys_form")
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
public class SysForm {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "title")
	private String title;
	@Column(name = "width")
	private Integer width;
	@Column(name = "form_config")
	private String formConfig;
	@Column(name = "widgets")
	private String widgets;
	@Column(name = "modify_time")
	private Date modifyTime;
	@Column(name = "creator_id")
	private Integer creatorId;
	@Column(name = "creator_name")
	private String creatorName;
	@Column(name = "if_valid")
	private Integer ifValid;
}
