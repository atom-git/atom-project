package com.atom.server.system.dao.impl;

import com.atom.common.dao.AbsDao;
import com.atom.server.system.dao.ISysLogDao;
import com.atom.server.system.entity.SysLog;
import org.springframework.stereotype.Repository;

/**
 * @author zr
 * @description 系统日志dao
 * @date 2021/4/22
 */
@Repository
public class SysLogDao extends AbsDao<SysLog> implements ISysLogDao {
}
