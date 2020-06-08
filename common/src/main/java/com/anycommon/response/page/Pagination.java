package com.anycommon.response.page;

public class Pagination {

    private int pageIndex = 1;
    private int pageSize = 10;
    private long totalCount;
    private int totalPage;

    public Pagination() {
    }

    public Pagination(int pageIndex, int pageSize, long totalCount) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPageIndex() {
        return this.pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public void setPageNum(int pageNum) {
        this.pageIndex = pageNum;
    }

    public int getPageNum() {
        return this.getPageIndex();
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStartIndex() {
        return this.getPageIndex() * this.getPageSize();
    }

    public int getEndIndex() {
        return this.getStartIndex() + this.getPageSize();
    }

    public int getOffset() {
        return this.getPageSize();
    }

}
