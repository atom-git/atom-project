package com.atom.server.system.dao;

import com.atom.common.dao.IDao;
import com.atom.common.pojo.mapper.NewsType;
import com.atom.common.pojo.table.PageData;
import com.atom.server.system.entity.SysNews;

import java.util.List;

/**
 * @author zr
 * @description 系统提醒消息待办信息dao接口
 * @date 2021/4/22
 */
public interface ISysNewsDao extends IDao<SysNews> {

	List<SysNews> findValidByUser(Integer userId, NewsType newsType, PageData pageData);
}
