package com.lxyer.base.common.base;

import org.springframework.http.HttpStatus;

import java.util.List;


/**
 * 分页对象
 *
 * @author lxyer
 */
public class PageBean<T> extends BaseResponse {
    private int limit; // 每页条数

    private int currentPage; // 当前页

    private int count; // 总条数

    private int pagers; // 总页码

    private List<T> data;

    public PageBean() {
        // 这个code默认为500
        setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    public static PageBean<String> ok() {
        return new PageBean<String>();
    }

    public static PageBean<String> ok(String msg) {
        PageBean<String> r = new PageBean<String>();
        r.setMsg(msg);
        return r;
    }

    public static PageBean<String> error() {
        PageBean<String> r = new PageBean<String>();
        r.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return r;
    }

    public static PageBean<String> error(String msg) {
        PageBean<String> r = new PageBean<String>();
        r.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        r.setMsg(msg);
        return r;
    }

    public static PageBean<String> error(int code, String msg) {
        PageBean<String> r = new PageBean<String>();
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int pageSize) {
        this.limit = pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int curPage) {
        this.currentPage = curPage;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int total) {
        this.count = total;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> rows) {
        this.data = rows;
    }

    public int getPagers() {
        this.pagers = (int) Math.ceil((double) this.count / this.limit);
        return this.pagers;
    }

    public void setPagers(int pagers) {
        this.pagers = pagers;
    }

}
