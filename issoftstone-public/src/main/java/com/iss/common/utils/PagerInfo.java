package com.iss.common.utils;

import java.util.List;

public class PagerInfo<T> {
	private long limit = 10; // 分页大小
	private long totals;// 总记录数
	private long totalPage;// 总页数
	private long page;// 当前页

	private List<T> content;

	public PagerInfo() {
	}

	public PagerInfo(PageSupport support, List<T> content) {
		this.limit = support.getLimit();
		this.page = support.getPage();
		this.totals = support.getTotals();
		this.totalPage = support.getTotalPage();
		this.content = content;
	}

	public long getTotals() {
		return totals;
	}

	public long getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(long totalPage) {
		this.totalPage = totalPage;
	}

	public long getPage() {
		return page == 0 ? 1 : page;
	}

	public void setPage(long page) {
		this.page = page;
	}

	public long getLimit() {
		return limit;
	}

	public void setLimit(long limit) {
		this.limit = limit;
	}

	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}
}
