package com.iss.common.utils;

import javax.persistence.Transient;

public class PageSupport {
	public enum Sortable {
		ASC, /**
		 * DESC 倒序 ASC 顺序
		 */
		DESC
	}
	private int limit = 10; // 分页大小
	private String order = "createTime";
	private int page = 1;// 当前页
	private String sort = Sortable.DESC.toString().toLowerCase();
	private int totalPage;// 总页数

	private int totals;// 总记录数

	public PageSupport() {
	}

	@Transient
	public int getLimit() {
		return limit;
	}

	@Transient
	public String getOrder() {
		return order;
	}

	@Transient
	public int getPage() {
		if (page <= 0) {
			page = 1;
		} 
		return page;
	}

	@Transient
	public String getSort() {
		return sort;
	}

	@Transient
	public int getTotalPage() {
		return totalPage;
	}

	@Transient
	public int getTotals() {
		return totals;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public void setTotalRecord(int totalRecord) {
		if (totalRecord > 0) {
			boolean bool = totalRecord % this.limit > 0;
			this.totalPage = (bool ? (totalRecord / this.limit + 1) : (totalRecord / this.limit));
		}
		this.totals = totalRecord;
	}
}
