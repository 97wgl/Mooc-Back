package com.xgxfd.moocback.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xgxfd.moocback.entity.Teacher;
import com.xgxfd.moocback.entity.User;
import com.xgxfd.moocback.service.TeacherService;
import com.xgxfd.moocback.service.UserService;
import com.xgxfd.moocback.util.CommonUtil;
import com.xgxfd.moocback.util.MailSender;
import com.xgxfd.moocback.vo.MessageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Xxz Wgl
 * @since 2019-05-27
 */

@Slf4j
@CrossOrigin(origins = "*",
             maxAge = 3600,
             methods = {RequestMethod.DELETE,RequestMethod.POST,RequestMethod.GET,RequestMethod.PUT})
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    MailSender mailSender;

    private StringBuffer valid;


    @PostMapping("/regist")
    @ResponseBody
    public String teacherRegist(@RequestParam("email") String email,
                                @RequestParam("validCode") String validCode,
                                @RequestParam("username") String username,
                                @RequestParam("password") String password){
        MessageVO<String> messageVO ;
        if(!validCode.equals(valid.toString())){//验证码错误
            messageVO = new MessageVO<String>(-1,"验证码填写错误",null);
        } else{
            User user = userService.getOne(new QueryWrapper<User>().eq("name",username));
            if(user != null){//用户名已存在
                messageVO = new MessageVO<String>(-1,"用户名已存在",null);
            }else{//注册用户
                user = new User(); //对象为空 先new
                user.setName(username);
                user.setEmail(email);
                user.setRegistDate(LocalDateTime.now());
                user.setPwd(CommonUtil.MD5(password));
                Boolean flag = userService.save(user);
                if(flag){//注册成功
                    log.info("用户注册成功。注册人：" + username + "注册时间:" + LocalDateTime.now());
                    messageVO = new MessageVO<String>(0,"注册成功",null);
                }else{//注册失败
                    messageVO = new MessageVO<String>(-1,"注册失败",null);
                }
            }
        }
        return messageVO.getReturnResult(messageVO);
    }

    @PostMapping("/regist/valid")
    @ResponseBody
    public String emailSendValid(@RequestParam("email") String email){

        valid = new CommonUtil().registValid(valid);
        Map<String,Object> map = new HashMap<>();
        map.put("validCode",valid.toString());
        Boolean flag = mailSender.sendWithHTMLTemplate(email,"MOOC注册验证码","emailTemplate.ftl",map);
        MessageVO<StringBuffer> messageVO;
        if(flag) {
            messageVO = new MessageVO<StringBuffer>(0,"发送验证码成功",null);
        }
        else{
            messageVO = new MessageVO<StringBuffer>(-1,"发送验证码失败",null);
        }
        return messageVO.getReturnResult(messageVO);
    }

    @PostMapping("/login")
    @ResponseBody
    public String userLogin(@RequestParam("username") String username,
                            @RequestParam("password") String password){
        User user = userService.getOne(new QueryWrapper<User>().eq("name",username).eq("pwd",CommonUtil.MD5(password)));
        MessageVO<String> messageVO;
        if(user != null){//登录成功
            log.info("登录成功。 登录人：" + username + "登录时间："+ LocalDateTime.now());
            messageVO = new MessageVO<String>(0,"登录成功",null);
        }else{
            log.error("登录失败。 登录人：" + username + "登录时间："+ LocalDateTime.now());
            messageVO = new MessageVO<String>(-1,"用户名或密码错误",null);
        }
        return  messageVO.getReturnResult(messageVO);
    }


}
