package com.atom.server.system.controller;

import com.atom.common.pojo.UploadResult;
import com.atom.common.pojo.annotation.Permission;
import com.atom.common.pojo.http.RestResponse;
import com.atom.common.pojo.mapper.ActionType;
import com.atom.common.pojo.mapper.GrantType;
import com.atom.common.pojo.table.PageData;
import com.atom.common.pojo.table.TableData;
import com.atom.common.security.SessionUser;
import com.atom.server.system.pojo.dto.SysFileDTO;
import com.atom.server.system.pojo.dto.UploadDTO;
import com.atom.server.system.pojo.filter.SysFileFilter;
import com.atom.server.system.pojo.vo.SysFileVO;
import com.atom.server.system.service.ISysFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author zr
 * @description 系统附件管理，文件列表，文件夹列表，目录增加，上传文件
 * 单文件操作：删除文件，移动文件或者文件夹，复制文件或文件夹，重命名文件或文件夹
 * 批量文件操作：删除文件，移动文件或者文件夹，复制文件或文件夹，重命名文件或文件夹
 * @date 2021/4/23
 */
@RestController
@RequestMapping("/system/file")
@Api("系统附件管理")
@Permission
public class SysFileController {

	/**
	 * 文件操作的服务
	 */
	@Resource
	private ISysFileService sysFileService;

	/**
	 * 查询附件列表
	 * @param sessionUser   当前登陆人
	 * @param sysFileFilter 附件过滤条件
	 * @param pageData      分页参数
	 * @return 附件列表及分页数据
	 */
	@GetMapping("list")
	@ApiOperation("查询附件列表")
	@Permission(actionType = ActionType.Q, grantType = GrantType.AUTO)
	public RestResponse<TableData<SysFileVO>> list(@ApiIgnore SessionUser sessionUser, SysFileFilter sysFileFilter, PageData pageData) {
		return RestResponse.success(sysFileService.list(sessionUser, sysFileFilter, pageData));
	}

	/**
	 * 上传文件，主要用于组件使用过程中的附件上传，根据当前路由分不同目录进行上传
	 * @param sessionUser 当前操作人
	 * @param folder 文件夹路径
	 * @param file   文件
	 * @return 文件上传成功的结果
	 */
	@PostMapping("upload")
	@ApiOperation("上传文件")
	@Permission(actionType = ActionType.N, grantType = GrantType.MANUAL)
	public RestResponse<UploadResult> upload(SessionUser sessionUser, String folder, @RequestBody MultipartFile file) {
		return RestResponse.success(sysFileService.upload(sessionUser, folder, file));
	}

	/**
	 * 删除文件
	 * @param fileId 文件id
	 * @return 是否删除成功
	 */
	@DeleteMapping("delete/{fileId}")
	@ApiOperation("删除文件")
	@Permission(actionType = ActionType.D, grantType = GrantType.MANUAL)
	public RestResponse<Boolean> delete(@PathVariable Long fileId) {
		return RestResponse.success(sysFileService.delete(fileId));
	}

	/**
	 * 下载文件
	 * @param fileId 文件id
	 * @param response 响应
	 */
	@GetMapping("download/{fileId}")
	@ApiOperation("下载文件")
	@Permission(actionType = ActionType.Q, grantType = GrantType.MANUAL)
	public void download(@PathVariable Long fileId, HttpServletResponse response) {
		sysFileService.download(fileId, response);
	}

	/**
	 * 查询文件夹目录
	 * @return 附件列表及分页数据
	 */
	@GetMapping("folder/tree")
	@ApiOperation("查询文件夹目录")
	@Permission(actionType = ActionType.Q, grantType = GrantType.AUTO)
	public RestResponse<List<SysFileVO>> folderTree() {
		return RestResponse.success(sysFileService.folderTree());
	}

	/**
	 * 新增文件夹
	 * @param sysFileDTO 系统附件传输对象
	 * @return 返回文件夹信息
	 */
	@PostMapping("add/folder")
	@ApiOperation("新增文件夹")
	@Permission(actionType = ActionType.N, grantType = GrantType.MANUAL)
	public RestResponse<SysFileVO> addFolder(@RequestBody SysFileDTO sysFileDTO) {
		return RestResponse.success(sysFileService.addFolder(sysFileDTO));
	}

	/**
	 * 上传文件
	 * @param sessionUser 当前操作人
	 * @param file        文件
	 * @param parentId    父节点ID
	 * @return 文件上传成功的结果
	 */
	@PostMapping("upload/{parentId}")
	@ApiOperation("上传文件到文件夹")
	@Permission(actionType = ActionType.N, grantType = GrantType.MANUAL)
	public RestResponse<UploadResult> upload(SessionUser sessionUser, @NotNull @RequestBody MultipartFile file, @PathVariable Long parentId) {
		return RestResponse.success(sysFileService.upload(sessionUser, file, parentId));
	}

	/**
	 * 单一文件操作，删除|delete，移动|move，复制|copy，重命名|rename
	 * @param sysFileDTO 系统附件传输对象
	 * @return 返回是否操作成功
	 */
	@PostMapping("operate/{operate}")
	@ApiOperation("单一文件操作，删除|delete，移动|move，复制|copy，重命名|rename")
	@Permission(actionType = ActionType.E, grantType = GrantType.MANUAL)
	public RestResponse<?> operate(@PathVariable String operate, @RequestBody SysFileDTO sysFileDTO) {
		sysFileService.operate(operate, sysFileDTO);
		return RestResponse.success();
	}

	/**
	 * 批量文件操作，删除|delete，移动|move，复制|copy，重命名|rename
	 * @param sysFileDTOList 系统附件传输对象列表
	 * @return 返回是否操作成功
	 */
	@PostMapping("batch/operate/{operate}")
	@ApiOperation("批量文件操作，删除|delete，移动|move，复制|copy，重命名|rename")
	@Permission(actionType = ActionType.E, grantType = GrantType.MANUAL)
	public RestResponse<?> batchOperate(@PathVariable String operate, @RequestBody List<SysFileDTO> sysFileDTOList) {
		sysFileService.batchOperate(operate, sysFileDTOList);
		return RestResponse.success();
	}
}
