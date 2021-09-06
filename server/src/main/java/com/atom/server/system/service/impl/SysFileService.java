package com.atom.server.system.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.lang.Validator;
import com.atom.common.pojo.GlobalConstant;
import com.atom.common.pojo.UploadResult;
import com.atom.common.pojo.http.RestError;
import com.atom.common.pojo.table.PageData;
import com.atom.common.pojo.table.TableData;
import com.atom.common.security.SessionUser;
import com.atom.common.util.FileNameUtil;
import com.atom.server.system.dao.ISysFileDao;
import com.atom.server.system.entity.SysFile;
import com.atom.server.system.pojo.dto.SysFileDTO;
import com.atom.server.system.pojo.filter.SysFileFilter;
import com.atom.server.system.pojo.vo.SysFileVO;
import com.atom.server.system.service.ISysFileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author zr
 * @description 系统附件管理服务
 * @date 2021/4/23
 */
@Service
@Transactional
public class SysFileService implements ISysFileService {

	/**
	 * 系统附件Dao
	 */
	@Resource
	private ISysFileDao sysFileDao;

	/**
	 * 查询附件列表
	 * @param sessionUser   当前登陆人
	 * @param sysFileFilter 附件过滤条件
	 * @param pageData      分页参数
	 * @return 附件列表及分页数据
	 */
	@Override
	public TableData<SysFileVO> list(SessionUser sessionUser, SysFileFilter sysFileFilter, PageData pageData) {
		return null;
	}

	/**
	 * 查询文件夹目录
	 * @return 附件列表及分页数据
	 */
	@Override
	public List<SysFileVO> folderTree() {
		return null;
	}

	/**
	 * 新增文件夹
	 * @param sysFileDTO 系统附件传输对象
	 * @return 返回文件夹信息
	 */
	@Override
	public SysFileVO addFolder(SysFileDTO sysFileDTO) {
		return null;
	}


	/**
	 * 上传文件，主要用于组件使用过程中的附件上传，根据当前路由分不同目录进行上传，配置了静态资源的目录位置 atom.file.path
	 * @param sessionUser 当前操作人
	 * @param folder   文件上传参数
	 * @param file        文件
	 * @return 文件上传成功的结果
	 */
	@Override
	public UploadResult upload(SessionUser sessionUser, String folder, MultipartFile file) {
		// 判断文件存储路径是否存在，文件路径为${atom.file.address}后拼接文件夹路径
		File storeFolder = FileUtil.file(GlobalConstant.FILE_STORAGE, folder);
		Long folderId;
		if (storeFolder.exists()) {
			SysFile folderFile = sysFileDao.findOneByField("fileKey", "file/" + folder);
			if (Validator.isNotNull(folderFile)) {
				folderId = folderFile.getId();
			} else {
				// 创建文件夹记录
				folderFile = new SysFile();
				folderFile.setName(folder);
				folderFile.setFileType("folder");
				folderFile.setFileKey("/file/" + folder);
				folderFile.setFileUrl(storeFolder.getAbsolutePath());
				folderFile.setCreatorId(sessionUser.getId());
				folderFile.setCreatorName(sessionUser.getName());
				folderFile.setModifyTime(DateUtil.date());
				folderId = (Long) sysFileDao.save(folderFile);
			}
		} else {
			// 创建文件夹
			boolean folderExist = storeFolder.mkdirs();
			if (folderExist) {
				// 创建文件夹记录
				SysFile folderFile = new SysFile();
				folderFile.setName(folder);
				folderFile.setFileType("folder");
				folderFile.setFileKey("/file/" + folder);
				folderFile.setFileUrl(storeFolder.getAbsolutePath());
				folderFile.setCreatorId(sessionUser.getId());
				folderFile.setCreatorName(sessionUser.getName());
				folderFile.setModifyTime(DateUtil.date());
				folderId = (Long) sysFileDao.save(folderFile);
			} else {
				return UploadResult.error(RestError.ERROR9000.getErrorCode(), "文件夹不存在");
			}
		}
		// 获取文件名称，在文件名后面
		FileNameUtil.FileName fileName = FileNameUtil.getFileName(file.getOriginalFilename(), "/file/" + folder + "/");
		String fileUrl = storeFolder.getAbsolutePath() + "/" + fileName.getName();
		// 写入文件
		FileWriter fileWriter = FileWriter.create(FileUtil.file(fileUrl));
		try {
			fileWriter.writeFromStream(file.getInputStream());
			SysFile sysFile = new SysFile();
			sysFile.setName(fileName.getName());
			sysFile.setFileType(fileName.getSuffix());
			sysFile.setSize(file.getSize());
			// 设置父级文件夹
			sysFile.setParentId(folderId);
			sysFile.setFileKey(fileName.getNameKey());
			sysFile.setFileUrl(fileUrl);
			sysFile.setCreatorId(sessionUser.getId());
			sysFile.setCreatorName(sessionUser.getName());
			sysFile.setModifyTime(DateUtil.date());
			sysFileDao.save(sysFile);
			return UploadResult.success(sysFile);
		} catch (IOException e) {
			return UploadResult.error(RestError.ERROR9000.getErrorCode(), "文件读写异常");
		}
	}

	/**
	 * 上传文件，主要用于文件管理模块上传文件
	 * @param sessionUser 当前操作人
	 * @param file        文件
	 * @param parentId    父节点ID
	 * @return 文件上传成功的结果
	 */
	@Override
	public UploadResult upload(SessionUser sessionUser, MultipartFile file, Integer parentId) {
		return null;
	}

	/**
	 * 单一文件操作，删除|delete，移动|move，复制|copy，重命名|rename
	 * @param sysFileDTO 系统附件传输对象
	 */
	@Override
	public void operate(String operate, SysFileDTO sysFileDTO) {

	}

	/**
	 * 批量文件操作，删除|delete，移动|move，复制|copy，重命名|rename
	 * @param sysFileDTOList 系统附件传输对象列表
	 */
	@Override
	public void batchOperate(String operate, List<SysFileDTO> sysFileDTOList) {

	}
}
