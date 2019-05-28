package com.xgxfd.moocback.controller;


import com.xgxfd.moocback.entity.Course;
import com.xgxfd.moocback.service.CourseService;
import com.xgxfd.moocback.vo.MessageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <p>
 * 课程表 前端控制器
 * </p>
 *
 * @author Xxz Wgl
 * @since 2019-05-27
 */
@Slf4j
@Controller
@RequestMapping("/course")
public class CourseController {
    @Autowired
    CourseService courseService;

    @RequestMapping("carousel")
    @ResponseBody
    public MessageVO<List<Course>> courselList() {
        List<Course> list = courseService.list();
        MessageVO<List<Course>> messageVO = new MessageVO<>();
        if (list.size() == 0) {
            messageVO.setCode(-1);
            messageVO.setMsg("课程数为0!");
        } else {
            messageVO.setCode(0);
            messageVO.setMsg("success");
            messageVO.setData(list);
        }
        return messageVO;
    }


}
