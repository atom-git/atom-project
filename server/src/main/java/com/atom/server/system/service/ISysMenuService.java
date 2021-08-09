package com.atom.server.system.service;

import com.atom.server.system.entity.SysMenu;
import com.atom.server.system.pojo.dto.SysMenuDTO;
import com.atom.server.system.pojo.vo.SysMenuVO;

import java.util.List;

/**
 * @author zr
 * @description 系统菜单管理服务接口
 * @date 2021/4/23
 */
public interface ISysMenuService {

	List<SysMenuVO> tree();

	void saveOrUpdate(SysMenuDTO sysMenuDTO);

	void delete(Integer menuId);

	SysMenuVO exchange(String move, SysMenuDTO sysMenuDTO);
}
