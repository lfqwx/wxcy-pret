package com.post.ibaties.secondary.xxx.send.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author: 𝓛.𝓕.𝓠
 */
@TableName("LFQ_SEND_DATA")
@KeySequence("SEQ_LFQ_SEND")
@Data
public class SendData {
    @TableId(value = "id")
    private Integer id;
    @TableField("jg_name")
    private String jgName;
    private Integer ryjl;//日邮件量
    private Double rsr;//日收入
    private Integer yyjl;//月邮件量
    private Double ysr;//月收入
}
