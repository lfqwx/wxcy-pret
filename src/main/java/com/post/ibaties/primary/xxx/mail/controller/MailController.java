package com.post.ibaties.primary.xxx.mail.controller;

import com.post.ibaties.common.core.ResponseMessage;
import com.post.ibaties.primary.xxx.mail.entity.Mail;
import com.post.ibaties.primary.xxx.mail.service.MailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController  /*等同于：@Controller和@ResponseBody配合使用*/
@RequestMapping("mail")
@Api(value="EMS_YJXX",description = "邮件信息") //@ApiModel
public class MailController {
    @Autowired
    MailService mailService;

    @GetMapping("/findOneById/{id:.+}")
    @ApiOperation(value="自定义：通过ID查找邮件",responseReference = "get")
    public ResponseMessage findOneById(@PathVariable String id){
        System.out.println(id);
        Mail mail=mailService.findOneById(id);
        //Mail send=mailService.getById(id);
        System.out.println(mail.getMailnum());
        return ResponseMessage.ok();
    }
    @PostMapping("/insertById")
    public void insert(){
        Mail mail = new Mail();
        mail.setMailnum("123");
        boolean save = mailService.save(mail);
        System.out.println(save);
    }
}
