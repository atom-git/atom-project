package com.atom.common.pojo.table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author zr
 * @description 排序实体
 * @date 2020/3/10
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("排序实体")
public class OrderData {
	// 升序常量
	public static final String ASC = "ASC";
	// 降序常量
	public static final String DESC = "DESC";
	// 默认的排序
	public static final OrderData DEFAULT_ORDER = new OrderData("createTime", "desc");

	/**
	 * 排序字段
	 */
	@ApiModelProperty("排序字段")
	private String orderColumn;

	/**
	 * 排序类型
	 */
	@ApiModelProperty("排序类型")
	private String orderType;

}
