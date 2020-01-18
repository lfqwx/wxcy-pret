package com.post.ibaties.common.core;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

public interface TreeSuportEntity {
    Serializable getId();
    @JSONField(name="pid")
    Serializable getParentId();
    @JSONField(name="rows")
    List<TreeSuportEntity> getChildren();
}
