package com.xgxfd.moocback.entity;

import org.springframework.stereotype.Component;

@Component
/*
  个人信息修改 需同时更新 hostholder
 */
public class HostHolder<T>{
    private  ThreadLocal<T>  users = new ThreadLocal<>();
    public T getUser(){
        return users.get(); //类比 Map<ThreadID,User>
    }
    public void setUser(T user){
        users.set(user);
    }
    public void clear(){
        users.remove();
    }
}
