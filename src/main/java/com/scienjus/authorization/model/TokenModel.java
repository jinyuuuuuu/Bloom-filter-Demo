package com.scienjus.authorization.model;

import java.util.HashMap;

/**
 * Token的Model类，可以增加字段提高安全性，例如时间戳、url签名
 *
 * @author zwl
 */
public class TokenModel {

    //用户id
    private long userId;

    //随机生成的uuid
    private String token;
    HashMap h = new HashMap();
    public TokenModel(long userId, String token) {
        this.userId = userId;
        this.token = token;

    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
