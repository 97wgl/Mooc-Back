package com.xgxfd.moocback.controller;


import com.xgxfd.moocback.entity.CourseComment;
import com.xgxfd.moocback.service.CourseCommentService;
import com.xgxfd.moocback.vo.CourseCommentVO;
import com.xgxfd.moocback.vo.MessageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.lang.reflect.Member;
import java.time.LocalDateTime;
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


    @GetMapping("/section/all")
    @ResponseBody
    public String getAllCourseSectionComment(@RequestParam("courseId") Integer courseId,
                                             @RequestParam(value = "sectionId",required = false) Integer sectionId){
        List<CourseCommentVO> courseCommentVOList;
        if(sectionId != null){
            courseCommentVOList = courseCommentService.getAllCourseComment(courseId,sectionId);
        }else{
            courseCommentVOList = courseCommentService.getCourseComment(courseId);
        }
        MessageVO<List<CourseCommentVO>> messageVO;
        if(courseCommentVOList.size() > 0){
            messageVO = new MessageVO<List<CourseCommentVO>>(0,"获取留言与所有回复成功",courseCommentVOList);
        }else {
            messageVO = new MessageVO<List<CourseCommentVO>>(-1,"获取当前章节留言与所有回复失败",null);
        }
        return messageVO.getReturnResult(messageVO);
    }


}
