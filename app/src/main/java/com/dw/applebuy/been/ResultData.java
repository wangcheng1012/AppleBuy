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
    private List<T> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
