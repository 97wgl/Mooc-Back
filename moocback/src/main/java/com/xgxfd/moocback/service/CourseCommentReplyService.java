package com.xgxfd.moocback.service;

import com.xgxfd.moocback.entity.CourseCommentReply;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xgxfd.moocback.vo.CourseCommentReplyVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Xxz Wgl
 * @since 2019-05-27
 */
public interface CourseCommentReplyService extends IService<CourseCommentReply> {


    List<CourseCommentReplyVO> getAllCourseCommentReply(Integer commentId);

    List<CourseCommentReplyVO> getUserBeReplyCourseComment(Integer uId);
}
