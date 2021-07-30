package com.atom.server.system.dao;

import com.atom.common.dao.IDao;
import com.atom.server.system.entity.SysFile;

import java.util.List;

/**
 * @author tansd
 * @description 附件管理 DAO 接口
 * @date 2021/4/22
 */
public interface ISysFileDao extends IDao<SysFile> {

    List<SysFile> findByIds(List<Integer> ids);

    List<SysFile> findByParentIds(Integer... id);

    List<SysFile> findAllFolders();

}
