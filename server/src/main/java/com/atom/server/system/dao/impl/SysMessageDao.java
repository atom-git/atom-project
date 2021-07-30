package com.atom.server.system.dao.impl;

import com.atom.common.dao.AbsDao;
import com.atom.server.system.dao.ISysMessageDao;
import com.atom.server.system.entity.SysMessage;
import org.springframework.stereotype.Repository;

/**
 * @author zr
 * @description 系统用户消息dao
 * @date 2021/4/22
 */
@Repository
public class SysMessageDao extends AbsDao<SysMessage> implements ISysMessageDao {
}
