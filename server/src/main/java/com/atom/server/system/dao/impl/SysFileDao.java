package com.atom.server.system.dao.impl;

import com.atom.common.dao.AbsDao;
import com.atom.server.system.dao.ISysFileDao;
import com.atom.server.system.entity.SysFile;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zr
 * @description 附件管理 dao
 * @date 2021/4/22
 */
@Repository
public class SysFileDao extends AbsDao<SysFile> implements ISysFileDao {

    @Override
    public List<SysFile> findByIds(List<Integer> ids) {
        DetachedCriteria dc = DetachedCriteria.forClass(SysFile.class);
        dc.add(Restrictions.in("id", ids));
        return findByDC(dc);
    }

    @Override
    public List<SysFile> findByParentIds(Integer... ids) {
        DetachedCriteria dc = DetachedCriteria.forClass(SysFile.class);
        dc.add(Restrictions.in("parentId", (Object[]) ids));
        return findByDC(dc);
    }

    @Override
    public List<SysFile> findAllFolders() {
        DetachedCriteria dc = DetachedCriteria.forClass(SysFile.class);
        dc.add(Restrictions.eq("fileType","folder"));
        return findByDC(dc);
    }
}
