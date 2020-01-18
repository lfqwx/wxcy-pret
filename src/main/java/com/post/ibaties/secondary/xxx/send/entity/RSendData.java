package com.post.ibaties.secondary.xxx.send.entity;

import lombok.Data;

/**
 * @author: 𝓛.𝓕.𝓠
 */
@Data
public class RSendData extends SendData{
    private String jgName;
    private Integer jhrywl;//计划业务
    private Integer jhrsr;//计划收入
    private Integer ryjl;//日邮件量
    private Double rsr;//日收入

    public RSendData() {
    }

    public RSendData(String jgName, Integer jhrywl, Integer jhrsr, Integer ryjl, Double rsr) {
        this.jgName = jgName;
        this.jhrywl = jhrywl;
        this.jhrsr = jhrsr;
        this.ryjl = ryjl;
        this.rsr = rsr;
    }
}
