package com.iss.common.utils;

import javax.persistence.Transient;

/**
 * @author Hyuan
 */
public class PageSupport {
    /**
     * DESC 倒序 ASC 正序
     */
    public enum Sortable {
        /**
         * 正序
         */
        ASC,

        /**
         * 倒序
         */
        DESC
    }

    /**
     * 分页大小
     */
    private int limit = 10;
    /**
     * 排序字段
     */
    private String order = "createTime";
    /**
     * 当前页
     */
    private int page = 1;
    private String sort = Sortable.DESC.toString().toLowerCase();
    /**
     * 总页数
     */
    private int totalPage;

    /**
     * 总记录数
     */
    private int totals;

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
