package com.post.ibaties.primary.xxx.mail.service;


import com.post.ibaties.common.core.BaseService;
import com.post.ibaties.primary.xxx.mail.entity.Mail;
import com.post.ibaties.primary.xxx.mail.mapper.MailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MailService extends BaseService<MailMapper, Mail> {
    @Autowired
    private MailMapper mailMapper;

    /*mybaties方式*/
    public Mail findOneById(String id){
        Mail mail=mailMapper.findOneById(Long.parseLong(id));//Long.parseLong(id)
        return mail;
    }
}
