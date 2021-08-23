package com.atom.server.system.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * @author zr
 * @description 系统提醒消息待办信息内容
 * @date 2021/8/23
 */
@Entity
@Table(name = "sys_news_content")
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
public class SysNewsContent {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "news_id")
	private Integer newsId;
	@Column(name = "content")
	private String content;
	@Column(name = "files")
	private String files;
	@Column(name = "extra_info")
	private String extraInfo;
}
