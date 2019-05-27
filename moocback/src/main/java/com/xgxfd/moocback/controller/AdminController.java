package com.xgxfd.moocback.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xgxfd.moocback.entity.Admin;
import com.xgxfd.moocback.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Xxz Wgl
 * @since 2019-05-27
 */
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



}
