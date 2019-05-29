package com.xgxfd.moocback.controller;


import com.xgxfd.moocback.entity.CourseCommentReply;
import com.xgxfd.moocback.service.CourseCommentReplyService;
import com.xgxfd.moocback.service.CourseCommentService;
import com.xgxfd.moocback.vo.MessageVO;
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
@Controller
@RequestMapping("/course-comment-reply")
public class CourseCommentReplyController {

    @Autowired
    CourseCommentReplyService courseCommentReplyService;

    @PostMapping("")
    @ResponseBody
    public String postCourseCommentReply(@RequestParam("commentId") Integer commentId ,
                                         @RequestParam("replyUId") Integer replyUId,
                                         @RequestParam("replyToUId") Integer replyToUId,
                                         @RequestParam("content") String content){

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
