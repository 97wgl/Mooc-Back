package com.xgxfd.moocback.controller;


import com.xgxfd.moocback.entity.CourseComment;
import com.xgxfd.moocback.service.CourseCommentService;
import com.xgxfd.moocback.vo.MessageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Member;
import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Xxz Wgl
 * @since 2019-05-27
 */
@Controller
@RequestMapping("/course-comment")
public class CourseCommentController {


    @Autowired
    CourseCommentService courseCommentService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String postCourseComment(@RequestParam("uId") Integer uId,
                                    @RequestParam("courseId") Integer courseId,
                                    @RequestParam("sectionId") Integer sectionId,
                                    @RequestParam("content") String content){

        CourseComment courseComment = new CourseComment();
        courseComment.setUId(uId);
        courseComment.setContent(content);
        courseComment.setSectionId(sectionId);
        courseComment.setCourseId(courseId);
        courseComment.setCreateTime(LocalDateTime.now());
        courseComment.setStatus("0");
        Boolean flag = courseCommentService.save(courseComment);
        MessageVO<String> messageVO;
        if(flag){
            messageVO = new MessageVO<String>(0,"用户留言成功",null);

        }else{
            messageVO = new MessageVO<String>(-1,"用户留言失败",null);
        }
        return messageVO.getReturnResult(messageVO);

    }

}
