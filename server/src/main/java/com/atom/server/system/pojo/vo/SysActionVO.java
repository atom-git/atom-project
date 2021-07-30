package com.atom.server.system.pojo.vo;

import com.atom.common.dao.Converter;
import com.atom.common.pojo.AbsEntity;
import com.atom.common.pojo.mapper.GrantType;
import com.atom.server.system.entity.SysAction;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.Set;

/**
 * @author zr
 * @description 系统资源响应实体
 * @date 2021/4/22
 */
@Getter
@Setter
@ApiModel("系统动作资源响应实体")
public class SysActionVO extends AbsEntity {

	@ApiModelProperty("资源ID")
	private Integer id;
	@ApiModelProperty("资源名称")
	private String name;
	@ApiModelProperty("动作响应地址")
	private String url;
	@ApiModelProperty("资源类型:1查询，2新增，3修改，4删除")
	private Integer type;
	@ApiModelProperty("资源所属主题ID")
	private Integer topicId;
	@ApiModelProperty("资源所属主题名称")
	private String topicName;
	@ApiModelProperty("授权类型 0：手动 1：自动")
	private Integer grantType;
	@ApiModelProperty("是否选中该资源")
	private Boolean checked;

	/**
	 * 传出层与数据库实体的相互转化类
	 */
	public static class VOConverter extends Converter<SysActionVO, SysAction> {
		@Override
		public SysActionVO doForward(SysAction sysAction) {
			SysActionVO sysActionVO = new SysActionVO();
			BeanUtils.copyProperties(sysAction, sysActionVO);
			return sysActionVO;
		}

		/**
		 * 增加是否被选中的checked
		 * @param sysAction 系统资源
		 * @param actionSet 角色资源选中情况
		 * @return 返回VO
		 */
		public SysActionVO doForward(SysAction sysAction, Set<Integer> actionSet) {
			SysActionVO sysActionVO = new SysActionVO();
			BeanUtils.copyProperties(sysAction, sysActionVO);
			sysActionVO.setChecked(actionSet.contains(sysActionVO.getId()) || sysActionVO.getGrantType().equals(GrantType.AUTO.getCode()));
			return sysActionVO;
		}
	}
}
