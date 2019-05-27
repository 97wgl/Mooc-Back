package com.xgxfd.moocback.vo;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.List;

@Data
public class MessageVO {

    private int code;// 0
    private String msg;
    private List<Object> data; //此处 泛型不太会 优化时提交注释
    public  MessageVO(){

    }

    public MessageVO(int code, String msg,List<Object> data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public String getReturnResult(MessageVO messageVO){
        JSONObject json = new JSONObject(true);
        json.put("code",code);
        json.put("msg",msg);
        json.put("data",data);
        return json.toJSONString();
    }


}
