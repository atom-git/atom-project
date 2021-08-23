package com.atom.server.system.dao.impl;

import com.atom.common.dao.AbsDao;
import com.atom.server.system.dao.ISysNewsContentDao;
import com.atom.server.system.entity.SysNewsContent;
import org.springframework.stereotype.Repository;

/**
 * @author zr
 * @description 系统提醒消息待办信息内容dao
 * @date 2021/4/22
 */
@Repository
public class SysNewsContentDao extends AbsDao<SysNewsContent> implements ISysNewsContentDao {
}
