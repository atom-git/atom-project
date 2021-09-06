package com.atom.server.system.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author zr
 * @description 系统附件
 * @date 2021/4/22
 */
@Entity
@Table(name = "sys_file")
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
public class SysFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "file_name")
    private String name;
    @Column(name = "file_type")
    private String fileType;
    @Column(name = "file_size")
    private Long size;
    @Column(name = "parent_id")
    private Long parentId;
    @Column(name = "file_key")
    private String fileKey;
    @Column(name = "file_url")
    private String fileUrl;
    @Column(name = "creator_id")
    private Integer creatorId;
    @Column(name = "creator_name")
    private String creatorName;
    @Column(name = "modify_time")
    private Date modifyTime;
}
