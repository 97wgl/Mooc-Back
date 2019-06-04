package com.xgxfd.moocback.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xgxfd.moocback.entity.CourseCommentReply;
import com.xgxfd.moocback.entity.Teacher;
import com.xgxfd.moocback.entity.User;
import com.xgxfd.moocback.service.CourseCommentReplyService;
import com.xgxfd.moocback.service.CourseCommentService;
import com.xgxfd.moocback.service.TeacherService;
import com.xgxfd.moocback.service.UserService;
import com.xgxfd.moocback.vo.MessageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Xxz Wgl
 * @since 2019-05-27
 */
@Slf4j
@Controller
@RequestMapping("/course-comment-reply")
public class CourseCommentReplyController {

    @Autowired
    CourseCommentReplyService courseCommentReplyService;

    @Autowired
    UserService userService;

    @Autowired
    TeacherService teacherService;

    @PostMapping("")
    @ResponseBody
    public String postCourseCommentReply(@RequestParam("commentId") Integer commentId ,
                                         @RequestParam("replyUId") Integer replyUId,
                                         @RequestParam("replyToUId") Integer replyToUId,
                                         @RequestParam("content") String content,
                                         @RequestParam("type") String type){

        log.info(type);
        if(type.trim().equals("teacher")) { //查找教师对应的用户id
            String teacherName = teacherService.getOne(new QueryWrapper<Teacher>().eq("tea_id",replyUId)).getName();
            log.info("1.教师Id：" + replyUId +"教师名：" + teacherName);
            replyUId = userService.getOne(new QueryWrapper<User>().eq("name",teacherName)).getUId();
        }
        log.info("2.真正存储的用户Id：" + replyUId);
        CourseCommentReply courseCommentReply = new CourseCommentReply();
        courseCommentReply.setCommentId(commentId);
        courseCommentReply.setReplyUId(replyUId);
        courseCommentReply.setReplyToUId(replyToUId);
        courseCommentReply.setContent(content);
        courseCommentReply.setCreateTime(LocalDateTime.now());
        Boolean flag = courseCommentReplyService.save(courseCommentReply);
        MessageVO<String> messageVO;
        if(flag){
            messageVO = new MessageVO<String>(0,"对章节留言回复成功",null);
        }else{
            messageVO = new MessageVO<String>(-1,"对章节留言回复失败",null);
        }
        return messageVO.getReturnResult(messageVO);
    }

}
