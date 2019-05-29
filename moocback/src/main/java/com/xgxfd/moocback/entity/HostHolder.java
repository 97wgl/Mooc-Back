package com.xgxfd.moocback.entity;

import org.springframework.stereotype.Component;

@Component
/*
  个人信息修改 需同时更新 hostholder
 */
public class HostHolder{
    private static ThreadLocal<Object>  users = new ThreadLocal<>();//static 和 泛型 不能同时存在
    public Object getUser(){
        return users.get(); //类比 Map<ThreadID,User>
    }
    public void setUser(Object user){
        users.set(user);
    }
    public void clear(){
        users.remove();
    }
}
