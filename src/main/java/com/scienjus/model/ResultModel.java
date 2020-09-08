package com.scienjus.model;

import com.scienjus.config.ResultStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义返回结果
 *
 * @author zwl
 */
public class ResultModel implements Serializable {

    /**
     * 返回码
     */
    private int code;

    /**
     * 返回结果描述
     */
    private String message;

    /**
     * 返回内容
     */
    private Object content;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Object getContent() {
        return content;
    }

    public ResultModel() {
        this.content = new HashMap<>();
    }

    public ResultModel(int code, String message) {
        this.code = code;
        this.message = message;
        ;
    }

    public ResultModel(int code, String message, Object content) {
        this.code = code;
        this.message = message;
    }

    public ResultModel(ResultStatus status) {
        this.code = status.getCode();
        this.message = status.getMessage();
    }

    public ResultModel(ResultStatus status, Object content) {
        this.code = status.getCode();
        this.message = status.getMessage();
        this.content = content;
    }

    public ResultModel(ResultStatus status, String content) {
        this.code = status.getCode();
        this.message = status.getMessage();
        this.content = content;
    }

    public Map<String, Object> finish() {
        Map<String, Object> map = new HashMap<>();
        map.put("code", this.code);
        map.put("message", this.message);
        map.put("content", this.content);
        return map;
    }

    public static ResultModel ok(Object content) {
        return new ResultModel(ResultStatus.SUCCESS, content);
    }

    public static ResultModel ok(String content) {
        return new ResultModel(ResultStatus.SUCCESS, content);
    }

    public static ResultModel ok() {
        return new ResultModel(ResultStatus.SUCCESS);
    }

    public static ResultModel error(ResultStatus error) {
        return new ResultModel(error);
    }
}
