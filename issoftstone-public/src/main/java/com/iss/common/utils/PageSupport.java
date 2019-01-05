package com.iss.common.utils;

import org.apache.commons.lang3.StringUtils;

public class PageSupport {
	private int limit = 10; // 分页大小
	private int totalRecord;// 总记录数
	private int totalPage;// 总页数
	private int page;// 当前页
	private String sort_;
	private String order = "createTime";
	private String sort = Sortable.DESC.toString().toLowerCase();

	public PageSupport() { }

	public void setTotalRecord(int totalRecord) {
		if (totalRecord > 0) {
			boolean bool = totalRecord % this.limit > 0;
			this.totalPage = (bool ? (totalRecord / this.limit + 1) : (totalRecord / this.limit));
		}
		this.totalRecord = totalRecord;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalRecord() {
		return totalRecord;
	}

	public String getOrder() {
		return order;
	}

	public String getSort() {
		return sort;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public enum Sortable {
		/**
		 * DESC 倒序 ASC 顺序
		 */
		DESC, ASC
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getPage() {
		if (page > 0) {
			page = page - 1;
		}
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getSort_() {
		return sort_;
	}

	public void setSort_(String sort_) {
		String[] s = sort_.split(":");
		this.sort_ = sort_;
		if (s.length > 1) {
			order = s[0];
			if (StringUtils.equals(s[1].toLowerCase(), Sortable.ASC.name().toLowerCase()))
				sort = Sortable.ASC.name().toLowerCase();
			else
				sort = Sortable.DESC.name().toLowerCase();
		}
	}
}
