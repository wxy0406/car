package com.jiuqi.car.service.vm;

public class PageVM<T> {

    private int pageIndex;

    private int pageSize;

    private T param;

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public T getParam() {
        return param;
    }

    public void setParam(T param) {
        this.param = param;
    }

    @Override
    public String toString() {
        return "PageVM{" +
            "pageIndex=" + pageIndex +
            ", pageSize=" + pageSize +
            ", param=" + param +
            '}';
    }
}
