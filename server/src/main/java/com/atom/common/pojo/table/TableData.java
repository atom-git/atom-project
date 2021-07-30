package com.atom.common.pojo.table;

import com.atom.common.pojo.AbsEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author zr
 * @description 数据表的关联数据，包括分页数据，排序数据
 * 以及是否下载，图表和数据表的切换等功能参数封装，【后期可以考虑聚合操作也加进来】
 * @date 2020/3/10
 */
@Getter
@Setter
@NoArgsConstructor
@ApiModel("数据表实体")
public class TableData<T> extends AbsEntity {

	/**
	 * 分页信息
	 */
	@ApiModelProperty("分页信息")
	private PageData page;

	/**
	 * 当前页的数据
	 */
	@ApiModelProperty("当前页的数据")
	private List<T> data;

	/**
	 * 根据分页信息创建结果实体-下载无数据的情况下
	 * @param page 分页信息
	 * @param totalCnt 总记录数
	 */
	public TableData(PageData page, long totalCnt) {
		page.setTotalCnt(totalCnt);
		this.page = page;
	}

	/**
	 * 根据分页信息创建结果实体
	 * @param page 分页信息
	 * @param data 数据实体
	 * @param totalCnt 总记录数
	 */
	public TableData(PageData page, List<T> data, long totalCnt) {
		page.setTotalCnt(totalCnt);
		this.page = page;
		this.data = data;
	}
}
