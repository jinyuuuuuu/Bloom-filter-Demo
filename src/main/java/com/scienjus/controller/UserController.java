package com.scienjus.controller;

import com.scienjus.Service.UserService;
import com.scienjus.model.ResultModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
    /**
     * @Author: JinYu
     * @Description: 发送验证码
     * @param phoneNum
     * @Return: org.springframework.http.ResponseEntity<com.scienjus.model.ResultModel>
     * @Date: 11:49 2020/10/15
    */
    @PostMapping("/sendSms")
    public ResponseEntity<ResultModel> sendSms(@RequestParam("phoneNum")String phoneNum){
        userService.sendSms(phoneNum);
        return new ResponseEntity<>(ResultModel.ok("短信发送成功"), HttpStatus.OK);
    }
}
