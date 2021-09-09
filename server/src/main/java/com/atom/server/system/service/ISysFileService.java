package com.atom.server.system.service;

import com.atom.common.pojo.UploadResult;
import com.atom.common.pojo.table.PageData;
import com.atom.common.pojo.table.TableData;
import com.atom.common.security.SessionUser;
import com.atom.server.system.pojo.dto.SysFileDTO;
import com.atom.server.system.pojo.dto.UploadDTO;
import com.atom.server.system.pojo.filter.SysFileFilter;
import com.atom.server.system.pojo.vo.SysFileVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author zr
 * @description 系统附件管理服务接口
 * @date 2021/4/23
 */
public interface ISysFileService {
	TableData<SysFileVO> list(SessionUser sessionUser, SysFileFilter sysFileFilter, PageData pageData);

	UploadResult upload(SessionUser sessionUser, String folder, MultipartFile file);

	boolean delete(Long fileId);

	void download(Long fileId, HttpServletResponse response);

	List<SysFileVO> folderTree();

	SysFileVO addFolder(SysFileDTO sysFileDTO);

	UploadResult upload(SessionUser sessionUser, MultipartFile file, Long parentId);

	void operate(String operate, SysFileDTO sysFileDTO);

	void batchOperate(String operate, List<SysFileDTO> sysFileDTOList);
}
