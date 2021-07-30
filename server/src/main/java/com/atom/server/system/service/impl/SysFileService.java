package com.atom.server.system.service.impl;

import com.atom.common.pojo.UploadResult;
import com.atom.common.pojo.table.PageData;
import com.atom.common.pojo.table.TableData;
import com.atom.common.security.SessionUser;
import com.atom.server.system.pojo.dto.SysFileDTO;
import com.atom.server.system.pojo.filter.SysFileFilter;
import com.atom.server.system.pojo.vo.SysFileVO;
import com.atom.server.system.service.ISysFileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
	 * 上传文件
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
