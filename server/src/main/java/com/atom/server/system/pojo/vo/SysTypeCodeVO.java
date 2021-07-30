package com.atom.server.system.pojo.vo;

import com.atom.common.dao.Converter;
import com.atom.common.pojo.AbsEntity;
import com.atom.server.system.entity.SysType;
import com.atom.server.system.entity.SysTypeCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @author zr
 * @description 系统数据字典VO
 * @date 2021/4/22
 */
@Getter
@Setter
@ApiModel("系统数据字典VO")
public class SysTypeCodeVO extends AbsEntity {
	@ApiModelProperty("类型字典ID")
	private Integer id;
	@ApiModelProperty("类型名称")
	private String typeName;
	@ApiModelProperty("类型描述")
	private String typeDesc;
	@ApiModelProperty("类型父级编码，可以为空")
	private Integer parentId;
	@ApiModelProperty("优先级")
	private Integer typeOrder;
	@ApiModelProperty("字典编号ID")
	private Integer meanId;
	@ApiModelProperty("字典编号名称")
	private String meanName;
	@ApiModelProperty("数据字典")
	private SysType sysType;
	@ApiModelProperty("子类型")
	private List<SysTypeCodeVO> children;

	public static class VOConverter extends Converter<SysTypeCodeVO, SysTypeCode> {
		@Override
		public SysTypeCodeVO doForward(SysTypeCode sysTypeCode) {
			if (sysTypeCode == null) {
				return null;
			}
			SysTypeCodeVO sysTypeCodeVO = new SysTypeCodeVO();
			BeanUtils.copyProperties(sysTypeCode, sysTypeCodeVO);
			sysTypeCodeVO.setMeanName(sysTypeCode.getSysType().getMeanName());
			return sysTypeCodeVO;
		}
	}
}
