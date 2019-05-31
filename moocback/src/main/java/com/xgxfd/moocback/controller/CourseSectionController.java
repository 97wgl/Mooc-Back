package com.xgxfd.moocback.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xgxfd.moocback.entity.Course;
import com.xgxfd.moocback.entity.CourseSection;
import com.xgxfd.moocback.service.CourseSectionService;
import com.xgxfd.moocback.service.CourseService;
import com.xgxfd.moocback.vo.MessageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Xxz Wgl
 * @since 2019-05-27
 */
@Controller
@RequestMapping("/course-section")
public class CourseSectionController {
    @Autowired
    CourseSectionService courseSectionService;

    /**
     * 获取课程章节列表
     *
     * @return
     */
    @GetMapping("list")
    @ResponseBody
    public MessageVO<List<CourseSection>> courseSectionList(@RequestParam("courseId") String courseId) {
        QueryWrapper<CourseSection> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        List<CourseSection> list = courseSectionService.list(queryWrapper);
        MessageVO<List<CourseSection>> messageVO = new MessageVO<>();
        if (list.size() == 0) {
            messageVO.setCode(-1);
            messageVO.setMsg("课程列表为空!");
        } else {
            messageVO.setCode(0);
            messageVO.setMsg("success");
            messageVO.setData(list);
        }
        return messageVO;
    }

    @GetMapping("info")
    @ResponseBody
    public MessageVO<CourseSection> courseSectionInfo(@RequestParam("courseSectionId") String courseSectionId) {
        MessageVO<CourseSection> messageVO = new MessageVO<>();
        CourseSection courseSection = courseSectionService.getById(courseSectionId);
        if (courseSection != null) {
            messageVO.setCode(0);
            messageVO.setMsg("success");
            messageVO.setData(courseSection);
        } else {
            messageVO.setCode(-1);
            messageVO.setMsg("获取章节信息失败");
        }
        return messageVO;
    }
}
