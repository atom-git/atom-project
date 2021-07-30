package com.atom.server.system.dao.impl;

import cn.hutool.core.lang.Validator;
import com.atom.common.dao.AbsDao;
import com.atom.server.system.dao.ISysTypeCodeDao;
import com.atom.server.system.entity.SysTypeCode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * @author zr
 * @description 系统码表dao
 * @date 2021/4/22
 */
@Repository
public class SysTypeCodeDao extends AbsDao<SysTypeCode> implements ISysTypeCodeDao {

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
