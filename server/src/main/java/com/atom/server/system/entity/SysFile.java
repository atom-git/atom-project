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
    private Integer id;
    @Column(name = "file_name")
    private String name;
    @Column(name = "file_type")
    private String fileType;
    @Column(name = "file_size")
    private Long size;
    @Column(name = "file_authority")
    private Integer authority;
    @Column(name = "file_key")
    private String key;
    @Column(name = "bucket_name")
    private String bucketName;
    @Column(name = "parent_id")
    private Integer parentId;
    @Column(name = "creator_id")
    private Integer creatorId;
    @Column(name = "creator_name")
    private Integer creatorName;
    @Column(name = "modify_time")
    private Date modifyTime;
}
