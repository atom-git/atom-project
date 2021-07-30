package com.atom.server.system.dao.impl;

import com.atom.common.dao.AbsDao;
import com.atom.server.system.dao.ISysMessageTopicDao;
import com.atom.server.system.entity.SysMessageTopic;
import org.springframework.stereotype.Repository;

/**
 * @author zr
 * @description 系统用户消息主题表dao
 * @date 2021/4/22
 */
@Repository
public class SysMessageTopicDao extends AbsDao<SysMessageTopic> implements ISysMessageTopicDao {
}
