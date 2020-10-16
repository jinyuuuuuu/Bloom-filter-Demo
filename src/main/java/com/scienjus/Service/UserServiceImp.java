package com.scienjus.Service;

import com.google.common.base.Charsets;
import com.google.common.hash.Funnel;
import com.google.gson.Gson;
import com.scienjus.Utils.BloomFilterHelper;
import com.scienjus.Utils.RedisUtil;
import com.scienjus.authorization.annotation.Authorization;
import com.scienjus.domain.User;
import com.scienjus.repository.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.xml.ws.ServiceMode;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;

@Service
public class UserServiceImp implements UserService {
    @Resource
    UserRepository userRepository;
    @Resource
    RedisUtil redisUtil;
    @Resource
    private RabbitTemplate rabbitTemplate;
    private BloomFilterHelper<String> userBloomFilterHelper = new BloomFilterHelper<>((Funnel<String>) (from,into)->into. putString(from,Charsets.UTF_8),100,0.01);

    @Override
    public String findByNickyName(String name) {
        String a = String.valueOf(redisUtil.get(name));
        if(!redisUtil.includeByBloomFilter(userBloomFilterHelper,"userFilter",name))
            return "疑似穿透缓存操作";
        if (a == null || a.equals("null")) {
            User user = userRepository.findByNickname(name);

            /* HashMap<String, Object> map = new HashMap<>();
            map.put("id", user.getId());
            map.put("username", user.getUsername());
            map.put("password", user.getPassword());
            map.put("nickname", user.getNickname());*/
            Gson gson = new Gson();
            String str = gson.toJson(user);
            System.out.println(str+"\nservice");
            redisUtil.set(name, str);
            return str;
        } else {
            return a;
        }
    }

    @PostConstruct
    public String addAllDataToBloomFilter() {
        List<User> userList = userRepository.findAll();
        for (User user:userList) {
            redisUtil.addByBloomFilter(userBloomFilterHelper,"userFilter",user.getNickname());
        }
        System.out.println("布隆过滤器添加成功");
        return "添加成功";
    }

    @Override
    @Authorization
    public void sendSms(String phoneNum) {
        //lang3生成六位数字随机数
        String code = RandomStringUtils.randomNumeric(6);
        //放入Redis缓存中
        redisUtil.hset("phoneCode",phoneNum,code);
        rabbitTemplate.convertAndSend("topicModeExchange","good.phoneCode",code);
        System.out.println("短信已發送");
    }

    @Override
    public void JdkProxyTest() {
        System.out.println("目标类方法被执行了");
    }
}
