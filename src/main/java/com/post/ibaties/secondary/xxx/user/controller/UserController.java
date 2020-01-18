package com.post.ibaties.secondary.xxx.user.controller;

import com.post.ibaties.common.core.ResponseMessage;
import com.post.ibaties.secondary.xxx.user.entity.User;
import com.post.ibaties.secondary.xxx.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController  /*等同于：@Controller和@ResponseBody配合使用*/
@RequestMapping("user")
@Api(value="EMS_CZY",description = "操作员")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/findOneById/{id:.+}")
    @ApiOperation(value="自定义：通过ID查找用户",responseReference = "get")
    public ResponseMessage findOneById(@PathVariable String id){
        User user=userService.findUserById(id);
        System.out.println(user.getName());
        return ResponseMessage.ok();
    }

    @PostMapping("/insert")
    public void insert(){
        User user = new User();
        String seq_ems_czy = userService.generatorCode("seq_ems_czy", 6);
        user.setCode(seq_ems_czy);
        user.setName("操作员001");
        boolean save = userService.save(user);
        System.out.println(save);
    }
}
