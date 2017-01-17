package com.dw.applebuy.been;

import java.util.List;

/**
 * Created by Administrator on 2017/1/13.
 */
public class ResultData<T> {
    /**
     * total : 4
     * list : []
     */
    private int total;
    private T[] list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public T[] getList() {
        return list;
    }

    public void setList(T[] list) {
        this.list = list;
    }
}
