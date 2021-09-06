package com.atom.server.system.pojo.dto;

import com.atom.common.pojo.AbsEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

/**
 * @author zr
 * @description 文件上传dto
 * @date 2021/9/3
 */
@Getter
@Setter
@ApiModel("文件上传传输实体")
public class UploadDTO extends AbsEntity {

	@ApiModelProperty("文件上传目录")
	private String folder;
	@NotNull
	@ApiModelProperty("文件")
	private MultipartFile file;
}
