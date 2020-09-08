package com.scienjus.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mysql.cj.jdbc.util.ResultSetUtil;
import com.scienjus.Service.UserService;
import com.scienjus.authorization.annotation.Authorization;
import com.scienjus.authorization.annotation.CurrentUser;
import com.scienjus.authorization.manager.TokenManager;
import com.scienjus.authorization.model.TokenModel;
import com.scienjus.config.ResultStatus;
import com.scienjus.domain.User;
import com.scienjus.model.ResultModel;
import com.scienjus.repository.UserRepository;
import com.sun.deploy.net.HttpResponse;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 获取和删除token的请求地址，在Restful设计中其实就对应着登录和退出登录的资源映射
 *
 * @author zwl
 */
@RestController
@RequestMapping("/tokens")
public class TokenController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private TokenManager tokenManager;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation(value = "登录")
    public ResponseEntity<ResultModel> login(@RequestParam String username, @RequestParam String password) {
        Assert.notNull(username, "username can not be empty");
        Assert.notNull(password, "password can not be empty");

        User user = userRepository.findByUsername(username);
        if (user == null ||  //未注册
                !user.getPassword().equals(password)) {  //密码错误
            //提示用户名或密码错误
            return new ResponseEntity<>(ResultModel.error(ResultStatus.USERNAME_OR_PASSWORD_ERROR), HttpStatus.NOT_FOUND);
        }
        //生成一个token，保存用户登录状态
        TokenModel model = tokenManager.createToken(user.getId());
        return new ResponseEntity<>(ResultModel.ok(model), HttpStatus.OK);
    }

    @RequestMapping(value = "/findnick", method = RequestMethod.POST)
    public Map<String, Object> findyUserByNickName(HttpServletResponse httpResponse, @RequestParam("nickname") String nickname) throws Exception {
        String json = userService.findByNickyName(nickname);
        System.out.println(json);
        if(json.equals("疑似穿透缓存操作"))
            return ResultModel.ok(json).finish();
        JSONObject jsonObject = JSON.parseObject(json);
        /*httpResponse.setContentType("application/json;charset=utf-8");
        httpResponse.getWriter().write(json);*/
        //User user = userRepository.findByNickname(nickname);
        return ResultModel.ok(jsonObject).finish();
    }

    @PostMapping("/addAllDataToBloomFilter")
    public Map<String,Object> addAllDataToBloomFilter(){
        String s = userService.addAllDataToBloomFilter();
        return ResultModel.ok(s).finish();
    }
    @RequestMapping(value = "/logout", method = RequestMethod.DELETE)
    @Authorization
    @ApiOperation(value = "退出登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),
    })
    public ResponseEntity<ResultModel> logout(@CurrentUser User user) {
        tokenManager.deleteToken(user.getId());
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

}
