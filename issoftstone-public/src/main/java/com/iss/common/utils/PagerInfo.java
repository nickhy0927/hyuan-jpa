package com.iss.common.utils;

import java.util.List;

public class PagerInfo<T> {
	private long size = 10; // 分页大小
	private long totalRecord;// 总记录数
	private long totalPage;// 总页数
	private long currentPage;// 当前页

	private List<T> content;

	public PagerInfo() {
	}

	public PagerInfo(PageSupport support, List<T> content) {
		this.size = support.getLimit();
		this.currentPage = support.getPage();
		this.totalRecord = support.getTotalRecord();
		this.totalPage = support.getTotalPage();
		this.content = content;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public long getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(long totalRecord) {
		this.totalRecord = totalRecord;
	}

	public long getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(long totalPage) {
		this.totalPage = totalPage;
	}

	public long getCurrentPage() {
		return currentPage == 0 ? 1 : currentPage;
	}

	public void setCurrentPage(long currentPage) {
		this.currentPage = currentPage;
	}

	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Pager [size=" + size + ", totalRecord=" + totalRecord + ", totalPage=" + totalPage + ", currentPage="
				+ currentPage + ", content=" + content + "]";
	}
}
