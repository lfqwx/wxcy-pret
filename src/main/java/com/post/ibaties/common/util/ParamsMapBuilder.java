package com.post.ibaties.common.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/17.
 */
public class ParamsMapBuilder {
    private Map<String,Object> params;
    private ParamsMapBuilder(){
        params=new HashMap<>();
    }

    public static ParamsMapBuilder newBuilder(){
        return new ParamsMapBuilder();
    }

    public ParamsMapBuilder put(String key,Object val){
        this.params.put(key,val);
        return this;
    }

    public Map<String,Object> build(){
        return this.params;
    }
}
