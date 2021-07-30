package com.atom.server.system.service.impl;

import cn.hutool.core.lang.Validator;
import com.atom.common.pojo.exception.BusException;
import com.atom.common.pojo.http.RestError;
import com.atom.common.pojo.table.PageData;
import com.atom.common.pojo.table.TableData;
import com.atom.common.util.FileUtil;
import com.atom.common.util.TreeUtil;
import com.atom.server.system.dao.ISysTypeCodeDao;
import com.atom.server.system.dao.ISysTypeDao;
import com.atom.server.system.entity.SysType;
import com.atom.server.system.entity.SysTypeCode;
import com.atom.server.system.pojo.dto.SysTypeCodeDTO;
import com.atom.server.system.pojo.dto.SysTypeDTO;
import com.atom.server.system.pojo.filter.SysTypeCodeFilter;
import com.atom.server.system.pojo.vo.SysTypeCodeVO;
import com.atom.server.system.pojo.vo.SysTypeVO;
import com.atom.server.system.service.ISysTypeService;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zr
 * @description 系统数据字典管理服务
 * @date 2021/4/23
 */
@Service
@Transactional
public class SysTypeService implements ISysTypeService {

	/**
	 * 数字字典类型查询dao
	 */
	@Resource
	private ISysTypeDao sysTypeDao;

	/**
	 * 数字字典查询dao
	 */
	@Resource
	private ISysTypeCodeDao sysTypeCodeDao;

	/**
	 * 系统数据字典类型VO转换器
	 */
	private final SysTypeVO.VOConverter sysTypeVOConverter = new SysTypeVO.VOConverter();

	/**
	 * 系统数据字典类型VO转换器
	 */
	private final SysTypeCodeVO.VOConverter sysTypeCodeVOConverter = new SysTypeCodeVO.VOConverter();

	/**
	 * 系统数据字典类型DTOO转换器
	 */
	private final SysTypeDTO.DTOConverter sysTypeDTOConverter = new SysTypeDTO.DTOConverter();

	/**
	 * 系统数据字典DTO转换器
	 */
	private final SysTypeCodeDTO.DTOConverter sysTypeCodeDTOConverter = new SysTypeCodeDTO.DTOConverter();

	/**
	 * 系统数据字典类型Filter转换器
	 */
	private final SysTypeCodeFilter.FilterConverter sysTypeCodeFilterConverter = new SysTypeCodeFilter.FilterConverter();

	/**
	 * 查询数据字典的列表
	 * @param pageData 分页信息
	 * @param response 响应
	 * @return 返回列表
	 */
	@Override
	public TableData<SysTypeVO> list(PageData pageData, HttpServletResponse response) {
		// 如果是下载，则下载整个数据字典，否则只按照分页查询类型
		if (pageData.getDownload()) {
			// 查询列表
			List<SysTypeCode> sysTypeCodeList = sysTypeCodeDao.download(pageData.isAllRecord());
			// 查询记录数
			long totalCnt = sysTypeCodeDao.count();
			// TODO 转换类型时加上父级信息
			List<SysTypeCodeVO> sysTypeVOList = sysTypeCodeList.stream().map(sysTypeCodeVOConverter::doForward).collect(Collectors.toList());
			FileUtil.downlodExcel("系统数据字典", SysTypeCodeVO.class, sysTypeVOList, totalCnt, response);
			return new TableData<>(pageData, totalCnt);
		} else {
			// 查询列表
			List<SysType> sysTypeList = sysTypeDao.findPage(pageData);
			List<SysTypeVO> sysTypeVOList = sysTypeList.stream().map(sysTypeVOConverter::doForward).collect(Collectors.toList());
			// 查询记录数
			long totalCnt = sysTypeDao.count();
			return new TableData<>(pageData, sysTypeVOList, totalCnt);
		}
	}

	/**
	 * 新增或者编辑数据字典类型
	 * @param sysTypeDTO 系统数据字典类型DTO
	 */
	@Override
	public void saveOrUpdate(SysTypeDTO sysTypeDTO) {
		SysType sysType = sysTypeDTOConverter.doForward(sysTypeDTO);
		// 根据ID是否存在判断是否是新增
		if (Validator.isNotNull(sysType.getId())) {
			SysType originType = sysTypeDao.findOne(sysType.getId());
			if (Validator.isNull(originType)) {
				throw new BusException(RestError.ERROR2000, "修改的数据字典类型不存在");
			}
			originType.setMeanName(sysType.getMeanName());
			originType.setMeanDesc(sysType.getMeanDesc());
			originType.setMultiLevel(sysType.getMultiLevel());
			sysTypeDao.update(originType);
		} else {
			// 类型是否重复
			if (sysTypeDao.ifExist(sysType.getMeanName())) {
				throw new BusException(RestError.ERROR2000, "数据字典类型不能重复！");
			}
			sysTypeDao.save(sysType);
		}
	}

	/**
	 * 删除系统数据字典
	 * @param typeId 数据字典ID
	 */
	@Override
	public void delete(Integer typeId) {
		SysType sysType = sysTypeDao.findOne(typeId);
		if (Validator.isNull(sysType)) {
			throw new BusException(RestError.ERROR2000, "数据字典不能存在，请刷新页面！");
		} else {
			sysTypeDao.delete(sysType);
		}
	}

	/**
	 * 查询数据字典列表
	 * @param sysTypeCodeFilter 数据字典查询条件
	 * @return 返回数据字典列表
	 */
	@Override
	public List<SysTypeCodeVO> codeList(SysTypeCodeFilter sysTypeCodeFilter) {
		DetachedCriteria dc = sysTypeCodeFilterConverter.doForward(sysTypeCodeFilter);
		return sysTypeCodeDao.findByDC(dc).stream().map(sysTypeCodeVOConverter::doForward).collect(Collectors.toList());
	}

	/**
	 * 查询数据字典对结构，这里所有的过滤条件仅对树的第一级有效，是先找到树，然后再过滤
	 * @param sysTypeCodeFilter 数据字典查询条件
	 * @return 返回数据字典树
	 */
	@Override
	public List<SysTypeCodeVO> codeTree(SysTypeCodeFilter sysTypeCodeFilter) {
		DetachedCriteria dc = sysTypeCodeFilterConverter.doForward(sysTypeCodeFilter);
		List<SysTypeCode> sysTypeCodeList = sysTypeCodeDao.findByDC(dc);
		// 转换成VO
		List<SysTypeCodeVO> sysTypeCodeVOList = sysTypeCodeList.stream().map(sysTypeCodeVOConverter::doForward).collect(Collectors.toList());
		// 转换成树结构
		return TreeUtil.polymerizationTree(sysTypeCodeVOList, SysTypeCodeVO::getId, SysTypeCodeVO::getParentId, SysTypeCodeVO::setChildren);
	}

	/**
	 * 新增或者编辑数据字典
	 * @param meanId 字典类型ID
	 * @param sysTypeCodeDTO 字典数据
	 */
	@Override
	public void saveOrUpdateCode(Integer meanId, SysTypeCodeDTO sysTypeCodeDTO) {
		// 先判断字典类型是否存在
		SysType sysType = sysTypeDao.findOne(meanId);
		if (Validator.isNull(sysType)) {
			throw new BusException(RestError.ERROR2000, "无效的数据字典类型，请核查！");
		}
		SysTypeCode sysTypeCode = sysTypeCodeDTOConverter.doForward(sysTypeCodeDTO);
		if (Validator.isNotNull(sysTypeCode.getId())) {
			// 编辑
			SysTypeCode originTypeCode = sysTypeCodeDao.findOne(sysTypeCode.getId());
			if (Validator.isNull(originTypeCode)) {
				throw new BusException(RestError.ERROR2000, "无效的数据字典，请核查！");
			}
			originTypeCode.setTypeName(sysTypeCode.getTypeName());
			originTypeCode.setTypeDesc(sysTypeCode.getTypeDesc());
			originTypeCode.setParentId(sysTypeCode.getParentId());
			originTypeCode.setMeanId(meanId);
			originTypeCode.setTypeOrder(sysTypeCode.getTypeOrder());
			sysTypeCodeDao.update(originTypeCode);
		} else {
			// 新增判断类型名称是否重复
			if (sysTypeCodeDao.ifExist(sysTypeCode)) {
				throw new BusException(RestError.ERROR2000, "数据字典类型不能重复！");
			}
			sysTypeCode.setMeanId(meanId);
			sysTypeCodeDao.save(sysTypeCode);
		}
	}

	/**
	 * 删除数据字典
	 * @param codeId 数据字典ID
	 */
	@Override
	public void deleteCode(Integer codeId) {
		SysTypeCode sysTypeCode = sysTypeCodeDao.findOne(codeId);
		if (Validator.isNull(sysTypeCode)) {
			throw new BusException(RestError.ERROR2000, "数据字典类型不能存在，请刷新页面！");
		} else {
			sysTypeCodeDao.delete(sysTypeCode);
		}
	}

	/**
	 * 字典顺序上移或者下移
	 * @param move 上移还是下移moveup|movedown
	 * @param sysTypeCodeDTO 移动的菜单
	 */
	@Override
	public void exchange(String move, SysTypeCodeDTO sysTypeCodeDTO) {
		// 检查菜单是否存在
		SysTypeCode sysTypeCode = sysTypeCodeDao.findOne(sysTypeCodeDTO.getId());
		if (Validator.isNull(sysTypeCode)) {
			throw new BusException(RestError.ERROR2000, "调整顺序的数据字典不存在!");
		}
		// 查找菜单平级的所有菜单
		List<SysTypeCode> sysTypeCodeList = sysTypeCodeDao.findAllByFieldAsc("parentId", sysTypeCode.getParentId(), "typeOrder");
		if (sysTypeCodeList == null || sysTypeCodeList.size() <= 1) {
			throw new BusException(RestError.ERROR2000, "无同级数据字典，无法完成顺序调整!");
		}
		int curIndex = -1;
		// 找到当前菜单的位置
		for (int index = 0; index < sysTypeCodeList.size(); index++) {
			if (sysTypeCodeList.get(index).getId().equals(sysTypeCode.getId())) {
				curIndex = index;
				break;
			}
		}
		SysTypeCode preTypeCode, nextTypeCode;
		if (curIndex == -1) {
			throw new BusException(RestError.ERROR2000, "调整顺序的数据字典不存在!");
		} else if (curIndex == 0 && move.equals("moveup")) {
			throw new BusException(RestError.ERROR2000, "数据字典已经是第一位!");
		} else if (curIndex == sysTypeCodeList.size() && move.equals("movedown")) {
			throw new BusException(RestError.ERROR2000, "数据字典已经是最后一位!");
		} else {
			if (move.equals("moveup")) {
				preTypeCode = sysTypeCodeList.get(curIndex - 1);
				int preOrder = preTypeCode.getTypeOrder();
				preTypeCode.setTypeOrder(sysTypeCode.getTypeOrder());
				sysTypeCode.setTypeOrder(preOrder);
				sysTypeCodeDao.update(sysTypeCode);
				sysTypeCodeDao.update(preTypeCode);
			} else if (move.equals("movedown")) {
				nextTypeCode = sysTypeCodeList.get(curIndex + 1);
				int nextOrder = nextTypeCode.getTypeOrder();
				nextTypeCode.setTypeOrder(sysTypeCode.getTypeOrder());
				sysTypeCode.setTypeOrder(nextOrder);
				sysTypeCodeDao.update(sysTypeCode);
				sysTypeCodeDao.update(nextTypeCode);
			}
		}
	}
}
