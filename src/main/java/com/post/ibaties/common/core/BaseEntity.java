package com.post.ibaties.common.core;

import java.io.Serializable;

public abstract class BaseEntity implements Serializable {

    public abstract Serializable getId();
    public abstract void setId(Serializable id);
}
