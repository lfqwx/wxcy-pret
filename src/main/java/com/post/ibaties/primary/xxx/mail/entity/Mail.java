package com.post.ibaties.primary.xxx.mail.entity;


import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.persistence.Entity;
import javax.persistence.Id;

//@Entity
@TableName("EMS_YJXX")
@KeySequence("SEQ_YJXX")
public class Mail {
    @TableId("id")
    private Long id;
    @TableField("yjhm")
    private String mailnum;

    public String getMailnum() {
        return mailnum;
    }

    public void setMailnum(String mailnum) {
        this.mailnum = mailnum;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
