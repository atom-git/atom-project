package com.atom.server.system.dao.impl;

import cn.hutool.core.lang.Validator;
import com.atom.common.dao.AbsDao;
import com.atom.common.pojo.table.PageData;
import com.atom.server.system.dao.ISysTypeCodeDao;
import com.atom.server.system.entity.SysTypeCode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zr
 * @description 系统码表dao
 * @date 2021/4/22
 */
@Repository
public class SysTypeCodeDao extends AbsDao<SysTypeCode> implements ISysTypeCodeDao {

    /**
     * 根据类型查询维值
     * @param pageData 分页信息
     * @param sysTypeIds 类型ids
     * @return 返回维值列表
     */
    @Override
    public List<SysTypeCode> findByType(PageData pageData, Object... sysTypeIds) {
        DetachedCriteria dc = DetachedCriteria.forClass(SysTypeCode.class);
        if (Validator.isNotNull(sysTypeIds) && sysTypeIds.length > 0) {
            dc.add(Restrictions.in("meanId", sysTypeIds));
        }
        return this.findPage(dc, pageData);
    }

    /**
     * 根据类型查询维值
     * @param sysTypeIds 类型ids
     * @return 返回维值列表
     */
    @Override
    public long countByType(Object... sysTypeIds) {
        DetachedCriteria dc = DetachedCriteria.forClass(SysTypeCode.class);
        if (Validator.isNotNull(sysTypeIds) && sysTypeIds.length > 0) {
            dc.add(Restrictions.in("meanId", sysTypeIds));
        }
        return this.countByDC(dc);
    }

    /**
     * 判断字典类型是否存在
     * @param sysTypeCode 类型
     * @return 返回是否存在
     */
    @Override
    public boolean ifExist(SysTypeCode sysTypeCode) {
        DetachedCriteria dc = DetachedCriteria.forClass(SysTypeCode.class);
        dc.add(Restrictions.eq("meanId", sysTypeCode.getMeanId()));
        dc.add(Restrictions.eq("typeName", sysTypeCode.getTypeName()));
        if (Validator.isNull(sysTypeCode.getParentId())) {
            dc.add(Restrictions.isNull("parentId"));
        } else {
            dc.add(Restrictions.eq("parentId", sysTypeCode.getParentId()));
        }
        return this.findOneByDC(dc) != null;
    }
}
