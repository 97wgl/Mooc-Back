package com.xgxfd.moocback.controller;


import com.xgxfd.moocback.service.CourseEvaluationService;
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
@RequestMapping("/course-evaluation")
public class CourseEvaluationController {

    @Autowired
    CourseEvaluationService courseEvaluationService;

    @PostMapping("/")
    @ResponseBody
    public String postCourseEvaluation(){
        return "";
    }

}
