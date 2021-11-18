package com.atom.server.system.service.impl;

import com.atom.server.system.dao.ISysFormDao;
import com.atom.server.system.service.ISysFormService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zr
 * @description 系统自定义表单服务
 * @date 2021/11/18
 */
@Service
public class SysFormService implements ISysFormService {

	/**
	 * 系统自定义表单dao
	 */
	@Resource
	private ISysFormDao sysFormDao;
}

