package com.atom.server.system.service.impl;

import cn.hutool.core.lang.Validator;
import com.atom.common.pojo.exception.BusException;
import com.atom.common.pojo.http.RestError;
import com.atom.server.system.dao.ISysMenuDao;
import com.atom.server.system.dao.ISysMenuTopicDao;
import com.atom.server.system.entity.SysMenu;
import com.atom.server.system.pojo.dto.SysMenuDTO;
import com.atom.server.system.pojo.vo.SysMenuVO;
import com.atom.server.system.service.ISysMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zr
 * @description 系统菜单管理服务
 * @date 2021/4/23
 */
@Service
@Transactional
public class SysMenuService implements ISysMenuService {

	/**
	 * 系统菜单dao
	 */
	@Resource
	private ISysMenuDao sysMenuDao;

	/**
	 * 菜单所对应的动作资源主题dao
	 */
	@Resource
	private ISysMenuTopicDao sysMenuTopicDao;

	/**
	 * 系统菜单VO转换器
	 */
	private final SysMenuVO.VOConverter sysMenuVOConverter = new SysMenuVO.VOConverter();

	/**
	 * 系统菜单DTO转换器
	 */
	private final SysMenuDTO.DTOConverter sysMenuDTOConverter = new SysMenuDTO.DTOConverter();

	/**
	 * 查询菜单树结构
	 * @return 返回list结构的数据
	 */
	@Override
	public List<SysMenuVO> tree() {
		List<SysMenu> sysMenuList = sysMenuDao.findAllAsc("menuOrder");
		return sysMenuList.stream().map(sysMenuVOConverter :: doForward).collect(Collectors.toList());
	}

	/**
	 * 新增或者编辑菜单数据
	 * @param sysMenuDTO 系统菜单传输对象
	 */
	@Override
	public void saveOrUpdate(SysMenuDTO sysMenuDTO) {
		SysMenu sysMenu = sysMenuDTOConverter.doForward(sysMenuDTO);
		// 非顶级菜单时，上级菜单是否存在
		if (Validator.isNotEmpty(sysMenu.getParentId())) {
			// 自己不能是自己的上级
			if (sysMenu.getParentId().equals(sysMenu.getId())) {
				throw new BusException(RestError.ERROR2000, "自已不能是自己的上级菜单！");
			}
			SysMenu menuParent = sysMenuDao.findOne(sysMenu.getParentId());
			if (menuParent == null) {
				throw new BusException(RestError.ERROR2000, "上级菜单不存在，请刷新页面！");
			}
			// 修改时上级菜单不能是自己的子菜单
			if (Validator.isNotNull(sysMenu.getId())) {
				List<SysMenu> sysMenuList = sysMenuDao.findChildren(sysMenu.getId());
				if (sysMenuList != null && sysMenuList.size() > 0) {
					boolean conflict = sysMenuList.stream().anyMatch(menu -> sysMenu.getId().equals(menu.getParentId()) && sysMenu.getParentId().equals(menu.getId()));
					if (conflict) {
						throw new BusException(RestError.ERROR2000, "上级菜单不能是自已子菜单！");
					}
				}
			}
		}
		// 保存菜单信息
		sysMenuDao.saveOrUpdate(sysMenu);
		// 保存菜单所对应的动作资源信息
		sysMenuTopicDao.saveOrUpdate(sysMenu, sysMenuDTO.getTopicList());
	}

	/**
	 * 删除当前菜单，这里是物理删除，编辑里能够把其置成失效
	 * @param menuId 菜单的id
	 */
	@Override
	public void delete(Integer menuId) {
		// 查询当前菜单
		SysMenu sysMenu = sysMenuDao.findOne(menuId);
		// 查询子菜单
		List<SysMenu> sysMenuList = sysMenuDao.findChildren(menuId);
		// 删除菜单及子菜单
		if (sysMenuList != null && sysMenuList.size() > 0) {
			sysMenuList.add(sysMenu);
			sysMenuDao.deleteAll(sysMenuList);
		} else {
			sysMenuDao.delete(sysMenu);
		}
	}

	/**
	 * 菜单顺序上移或者下移
	 * @param move 上移还是下移moveup|movedown
	 * @param sysMenuDTO 移动的菜单
	 */
	@Override
	public void exchange(String move, SysMenuDTO sysMenuDTO) {
		// 检查菜单是否存在
		SysMenu sysMenu = sysMenuDao.findOne(sysMenuDTO.getId());
		if (Validator.isNull(sysMenu)) {
			throw new BusException(RestError.ERROR2000, "调整顺序的菜单不存在!");
		}
		// 查找菜单平级的所有菜单

		List<SysMenu> sysMenuList = sysMenuDao.findAllByFieldAsc("parentId", sysMenu.getParentId(), "menuOrder");
		if (sysMenuList == null || sysMenuList.size() <= 1) {
			throw new BusException(RestError.ERROR2000, "无同级菜单，无法完成顺序调整!");
		}
		int curIndex = -1;
		// 找到当前菜单的位置
		for (int index = 0; index < sysMenuList.size(); index++) {
			if (sysMenuList.get(index).getId().equals(sysMenu.getId())) {
				curIndex = index;
				break;
			}
		}
		SysMenu preMenu, nextMenu;
		if (curIndex == -1) {
			throw new BusException(RestError.ERROR2000, "调整顺序的菜单不存在!");
		} else if (curIndex == 0 && move.equals("moveup")) {
			throw new BusException(RestError.ERROR2000, "菜单已经是第一位!");
		} else if (curIndex == sysMenuList.size() && move.equals("movedown")) {
			throw new BusException(RestError.ERROR2000, "菜单已经是最后一位!");
		} else {
			if (move.equals("moveup")) {
				preMenu = sysMenuList.get(curIndex - 1);
				int preOrder = preMenu.getMenuOrder();
				preMenu.setMenuOrder(sysMenu.getMenuOrder());
				sysMenu.setMenuOrder(preOrder);
				sysMenuDao.update(sysMenu);
				sysMenuDao.update(preMenu);
			} else if (move.equals("movedown")) {
				nextMenu = sysMenuList.get(curIndex + 1);
				int nextOrder = nextMenu.getMenuOrder();
				nextMenu.setMenuOrder(sysMenu.getMenuOrder());
				sysMenu.setMenuOrder(nextOrder);
				sysMenuDao.update(sysMenu);
				sysMenuDao.update(nextMenu);
			}
		}
	}
}
