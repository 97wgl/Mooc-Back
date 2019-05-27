package com.xgxfd.moocback.controller;


import com.xgxfd.moocback.service.TeacherService;
import com.xgxfd.moocback.vo.MessageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Xxz Wgl
 * @since 2019-05-27
 */
@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    TeacherService teacherService;

    @PostMapping("/regist")
    @ResponseBody
    public String teacherRegist(){
        MessageVO messageVO = new MessageVO(0,"",null);
        return messageVO.getReturnResult(messageVO);
    }


}
