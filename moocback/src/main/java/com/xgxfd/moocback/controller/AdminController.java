package com.xgxfd.moocback.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xgxfd.moocback.entity.Admin;
import com.xgxfd.moocback.entity.HostHolder;
import com.xgxfd.moocback.entity.Teacher;
import com.xgxfd.moocback.service.AdminService;
import com.xgxfd.moocback.util.CommonUtil;
import com.xgxfd.moocback.vo.MessageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    HostHolder hostHolder;

    @GetMapping("/getList")
    @ResponseBody
    public String getList(@RequestParam("page") int page,
                          @RequestParam("rows") int limit){
      Page<Admin> adminPage = new Page<>(page,limit);
      IPage<Admin> adminIPage = adminService.page(adminPage,new QueryWrapper<Admin>());
      List<Admin> adminList = adminIPage.getRecords();
      return  adminList.toString();
    }

    @PostMapping(value = "/login")
    @ResponseBody
    public String userLogin(HttpServletRequest request, HttpServletResponse response){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Admin admin = adminService.getOne(new QueryWrapper<Admin>().eq("name",username).eq("pwd", CommonUtil.MD5(password)));
        MessageVO<Map<String,String>> messageVO;
        if(admin != null){//登录成功

            Cookie cookie = new Cookie("userInfo",username);
            Cookie cookie1 = new Cookie("type","admin");
            cookie.setPath("/");
            cookie1.setPath("/");
            cookie.setMaxAge(3600*24*5);
            cookie1.setMaxAge(3600*24*5);
            response.addCookie(cookie);
            response.addCookie(cookie1);

            hostHolder.setUser(admin);//设置hostholder

            log.info("登录成功。 登录人：" + username + "登录时间："+ LocalDateTime.now());

            Map<String,String> map = new HashMap<>();
            map.put("userInfo",username);
            map.put("type","admin");
            map.put("id",admin.getAdminId().toString());
            messageVO = new MessageVO<>(0,"登录成功",map);
        }else{
            log.error("登录失败。 登录人：" + username + "登录时间："+ LocalDateTime.now());
            messageVO = new MessageVO<>(-1,"用户名或密码错误",null);
        }

        return  messageVO.getReturnResult(messageVO);
    }

}
