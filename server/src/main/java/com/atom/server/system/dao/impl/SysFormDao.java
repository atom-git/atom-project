package com.atom.server.system.dao.impl;

import com.atom.common.dao.AbsDao;
import com.atom.server.system.dao.ISysFormDao;
import com.atom.server.system.entity.SysForm;
import org.springframework.stereotype.Repository;

/**
 * @author zr
 * @description 系统自定义表单dao
 * @date 2021/11/18
 */
@Repository
public class SysFormDao extends AbsDao<SysForm> implements ISysFormDao {
}
