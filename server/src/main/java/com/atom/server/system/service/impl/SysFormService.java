package com.atom.server.system.service.impl;

import cn.hutool.core.lang.Validator;
import com.atom.common.pojo.exception.BusException;
import com.atom.common.pojo.http.RestError;
import com.atom.common.pojo.mapper.IfValid;
import com.atom.common.pojo.table.PageData;
import com.atom.common.pojo.table.TableData;
import com.atom.common.security.SessionUser;
import com.atom.server.system.dao.ISysFormDao;
import com.atom.server.system.entity.SysForm;
import com.atom.server.system.pojo.dto.SysFormDTO;
import com.atom.server.system.pojo.filter.SysFormFilter;
import com.atom.server.system.pojo.vo.SysFormVO;
import com.atom.server.system.service.ISysFormService;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

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

	/**
	 * 系统自定义表单VO转换器
	 */
	private final SysFormVO.VOConverter sysFormVOConverter = new SysFormVO.VOConverter();

	/**
	 * 系统自定义表单Filter转换器
	 */
	private final SysFormFilter.FilterConverter sysFormFilterConverter = new SysFormFilter.FilterConverter();

	/**
	 * 系统自定义表单Dto转换器
	 */
	private final SysFormDTO.DTOConverter sysFormDTOConverter = new SysFormDTO.DTOConverter();

	/**
	 * 获取系统自定义表单数据表
	 * @param sysFormFilter 系统自定义表单filter
	 * @param pageData 分页信息
	 * @return 返回当前页的系统自定义表单列表
	 */
	@Override
	public TableData<SysFormVO> list(SysFormFilter sysFormFilter, PageData pageData) {
		// 转换为查询对象
		DetachedCriteria dc = sysFormFilterConverter.doForward(sysFormFilter);
		// 查询列表
		List<SysForm> sysFormList = sysFormDao.findPage(dc, pageData);
		List<SysFormVO> sysFormVOList = sysFormList.stream().map(sysFormVOConverter::doForward).collect(Collectors.toList());
		// 查询记录数
		long totalCnt = sysFormDao.countByDC(dc);
		return new TableData<>(pageData, sysFormVOList, totalCnt);
	}

	/**
	 * 新增或者编辑系统自定义表单
	 * @param sessionUser 系统用户
	 * @param sysFormDTO 系统自定义表单数据
	 */
	@Override
	public void saveOrUpdate(SessionUser sessionUser, SysFormDTO sysFormDTO) {
		SysForm sysForm = sysFormDTOConverter.doForward(sysFormDTO);
		if (Validator.isNotNull(sysForm.getId())) {
			// 编辑表单：判断表单是否存在
			SysForm originForm = sysFormDao.findOne(sysForm.getId());
			if (Validator.isNull(originForm)) {
				throw new BusException(RestError.ERROR9000, "自定义表单不存在");
			}
			BeanUtils.copyProperties(sysFormDTO, originForm);
			sysFormDao.update(originForm);
		} else {
			// 判断表单名称是否重复，防止表单名称重复导致的业务不明确
			if (sysFormDao.exist("title", sysForm.getTitle())) {
				throw new BusException(RestError.ERROR9000, "自定义表单名称重复");
			}
			// 新增自定义表单
			sysForm.setCreatorId(sessionUser.getId());
			sysForm.setCreatorName(sessionUser.getName());
			sysForm.setIfValid(IfValid.VALID.getCode());
			sysFormDao.save(sysForm);
		}
	}

	/**
	 * 系统自定义表单禁用/启用
	 * @param formId 系统自定义表单id
	 */
	@Override
	public void toggleValid(Integer formId) {
		// 查询表单是否存在
		SysForm sysForm = sysFormDao.findOne(formId);
		if (Validator.isNotNull(sysForm)) {
			Integer ifValid = sysForm.getIfValid().equals(IfValid.VALID.getCode()) ? IfValid.INVALID.getCode() : IfValid.VALID.getCode();
			sysForm.setIfValid(ifValid);
			sysFormDao.update(sysForm);
		} else {
			throw new BusException(RestError.ERROR9000, "自定义表单不存在");
		}
	}
}

