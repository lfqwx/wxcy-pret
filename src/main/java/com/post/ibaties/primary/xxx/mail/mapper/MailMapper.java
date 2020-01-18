package com.post.ibaties.primary.xxx.mail.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.post.ibaties.primary.xxx.mail.entity.Mail;
import org.apache.ibatis.annotations.Select;

public interface MailMapper extends BaseMapper<Mail> {
    //自定义方法：列别名需和Entity字段名一致
    @Select("select id,yjhm as mailnum from ems_yjxx where id=#{id}")
    Mail findOneById(Long id);
}
