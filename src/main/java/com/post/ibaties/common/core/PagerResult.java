package com.post.ibaties.common.core;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Collections;
import java.util.List;

@ApiModel(description = "分页结果")
public class PagerResult<E> {
    private static final long serialVersionUID = -6171751136953308027L;

    public static <E> PagerResult<E> empty(){
        return new PagerResult(0, Collections.emptyList());
    }

    public static <E> PagerResult<E> of(int total,List<E> list){
        return new PagerResult<E>(total,list);
    }
    @ApiModelProperty("数据总数量")
    private long total;
    private int pos;
    @ApiModelProperty("查询结果")
    private List<E> data;

    public PagerResult() {
    }

    public PagerResult(long total, List<E> data) {
        this.total = total;
        this.data = data;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public long getTotal() {
        return total;
    }

    public PagerResult<E> setTotal(long total) {
        this.total = total;
        return this;
    }

    public List<E> getData() {
        return data;
    }

    public PagerResult<E> setData(List<E> data) {
        this.data = data;
        return this;
    }

}