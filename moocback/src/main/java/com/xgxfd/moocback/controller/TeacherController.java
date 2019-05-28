package com.xgxfd.moocback.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xgxfd.moocback.entity.HostHolder;
import com.xgxfd.moocback.entity.Teacher;
import com.xgxfd.moocback.service.TeacherService;
import com.xgxfd.moocback.util.CommonUtil;
import com.xgxfd.moocback.util.MailSender;
import com.xgxfd.moocback.vo.MessageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
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
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    TeacherService teacherService;

    @Autowired
    HostHolder<Teacher> hostHolder;

    @PostMapping("/login")
    @ResponseBody
    public String userLogin(@RequestParam("username") String username,
                            @RequestParam("password") String password,
                            HttpServletResponse response){
        Teacher teacher = teacherService.getOne(new QueryWrapper<Teacher>().eq("name",username).eq("pwd",CommonUtil.MD5(password)));
        MessageVO<String> messageVO;
        if(teacher != null){//登录成功

            Cookie cookie = new Cookie("userInfo",username);
            Cookie cookie1 = new Cookie("type","teacher");
            cookie.setPath("/");
            cookie1.setPath("/");
            cookie.setMaxAge(3600*24*5);
            cookie1.setMaxAge(3600*24*5);
            response.addCookie(cookie);
            response.addCookie(cookie1);

            hostHolder.setUser(teacher);//设置hostholder

            log.info("登录成功。 登录人：" + username + "登录时间："+ LocalDateTime.now());
            messageVO = new MessageVO<String>(0,"登录成功",null);
        }else{
            log.error("登录失败。 登录人：" + username + "登录时间："+ LocalDateTime.now());
            messageVO = new MessageVO<String>(-1,"用户名或密码错误",null);
        }
        return  messageVO.getReturnResult(messageVO);
    }

}
