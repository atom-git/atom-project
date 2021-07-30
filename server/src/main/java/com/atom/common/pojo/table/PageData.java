package com.atom.common.pojo.table;

import com.alibaba.fastjson.annotation.JSONField;
import com.atom.common.pojo.AbsEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author zr
 * @description 描述分页的结果集对象, hasMore由totalCnt计算得来
 * @date 2020/3/10
 */
@Getter
@Setter
@NoArgsConstructor
@ApiModel("数据分页实体")
@SuppressWarnings("unused")
public class PageData extends AbsEntity {

	/**
	 * 查询当前页，页码从1开始
	 */
	@ApiModelProperty("当前页码")
	private int curPage = 1;

	/**
	 * 每页记录数，默认10页
	 */
	@ApiModelProperty("当前页码")
	private int pageSize = 10;

	/**
	 * 每页开始行数
	 */
	@ApiModelProperty("开始行数")
	private int startIndex;

	/**
	 * 每页结束行数
	 */
	@ApiModelProperty("结束行数")
	private int endIndex;

	/**
	 * 是否全部记录
	 */
	@ApiModelProperty("是否全部记录")
	private boolean allRecord;

	/**
	 * 总记录数
	 */
	@ApiModelProperty("总记录数")
	private long totalCnt;

	/**
	 * 是否还有更多
	 */
	@ApiModelProperty("是否还有更多")
	private boolean hasMore;

	/**
	 * 排序信息
	 */
	@ApiModelProperty("排序信息")
	private OrderData OrderData;

	/**
	 * 是否数据下载
	 */
	@JSONField(serialize = false)
	@ApiModelProperty("是否数据下载")
	private Boolean download = false;

	/**
	 * 构造方法初始化页记录
	 * @param curPage  页码
	 * @param pageSize 每页记录数
	 */
	public PageData(int curPage, int pageSize) {
		this.curPage = curPage;
		this.pageSize = pageSize;
		this.startIndex = (curPage - 1) * pageSize;
		this.endIndex = curPage * pageSize;
	}

	/**
	 * 设置页码
	 * @param curPage 页码
	 */
	public void setCurPage(int curPage) {
		this.curPage = curPage;
		this.startIndex = (curPage - 1) * pageSize;
		this.endIndex = curPage * pageSize;
	}

	/**
	 * 设置每页记录数
	 * @param pageSize 每页记录数
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
		this.startIndex = (curPage - 1) * pageSize;
		this.endIndex = curPage * pageSize;
	}

	/**
	 * 设置总记录数
	 * @param totalCnt 总记录数
	 */
	public void setTotalCnt(long totalCnt) {
		this.totalCnt = totalCnt;
		long size = Long.parseLong(this.pageSize + "");
		int totalPage = (int) (this.totalCnt % size == 0 ? this.totalCnt / size : (this.totalCnt / size + 1));
		setHasMore(curPage < totalPage);
	}
}
