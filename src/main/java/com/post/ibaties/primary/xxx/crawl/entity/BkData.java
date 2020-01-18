package com.post.ibaties.primary.xxx.crawl.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author: 𝓛.𝓕.𝓠
 */
@TableName("LFQ_CRAWL_DATA")
@KeySequence("SEQ_LFQ_CRAWL")
@Data
public class BkData {
    @TableId(value = "id")
    private Integer id;
    @TableField("jg_name")
    private String jgName;
    private Integer ryjl;//日邮件量
    private Integer yyjl;//月邮件量
    private Double rsr;//日收入
    private Double ysr;//月收入

    public BkData( String jgName, Integer ryjl, Integer yyjl, Double rsr, Double ysr) {
        this.jgName = jgName;
        this.ryjl = ryjl;
        this.yyjl = yyjl;
        this.rsr = rsr;
        this.ysr = ysr;
    }

    public BkData() {
    }
}
