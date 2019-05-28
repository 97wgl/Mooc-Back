package com.xgxfd.moocback.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xgxfd.moocback.entity.Admin;
import com.xgxfd.moocback.entity.Teacher;
import com.xgxfd.moocback.service.AdminService;
import com.xgxfd.moocback.util.CommonUtil;
import com.xgxfd.moocback.vo.MessageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

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

    @GetMapping("/getList")
    @ResponseBody
    public String getList(@RequestParam("page") int page,
                          @RequestParam("rows") int limit){
      Page<Admin> adminPage = new Page<>(page,limit);
      IPage<Admin> adminIPage = adminService.page(adminPage,new QueryWrapper<Admin>());
      List<Admin> adminList = adminIPage.getRecords();
      return  adminList.toString();
    }

    @PostMapping("/login")
    @ResponseBody
    public String userLogin(@RequestParam("username") String username,
                            @RequestParam("password") String password){
        Admin admin = adminService.getOne(new QueryWrapper<Admin>().eq("name",username).eq("pwd", CommonUtil.MD5(password)));
        MessageVO<String> messageVO;
        if(admin != null){//登录成功
            messageVO = new MessageVO<String>(0,"登录成功",null);
        }else{
            messageVO = new MessageVO<String>(-1,"用户名或密码错误",null);
        }
        return  messageVO.getReturnResult(messageVO);
    }
//
//    @PostMapping("/login")
//    @ResponseBody
//    public String userLogin(@RequestBody Admin req){
//        log.info(req.getName());
//        log.info(req.getPwd());
//        Admin admin = adminService.getOne(new QueryWrapper<Admin>().eq("name",req.getName()).eq("pwd", CommonUtil.MD5(req.getPwd())));
//        MessageVO<String> messageVO;
//        if(admin != null){//登录成功
//            messageVO = new MessageVO<String>(0,"登录成功",null);
//        }else{
//            messageVO = new MessageVO<String>(-1,"用户名或密码错误",null);
//        }
//        return  messageVO.getReturnResult(messageVO);
//    }

}
