package com.xgxfd.moocback.service.impl;

import com.xgxfd.moocback.entity.CourseCommentReply;
import com.xgxfd.moocback.mapper.CourseCommentReplyMapper;
import com.xgxfd.moocback.service.CourseCommentReplyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xgxfd.moocback.vo.CourseCommentReplyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Xxz Wgl
 * @since 2019-05-27
 */
@Service
public class CourseCommentReplyServiceImpl extends ServiceImpl<CourseCommentReplyMapper, CourseCommentReply> implements CourseCommentReplyService {


    @Override
    public List<CourseCommentReplyVO> getAllCourseCommentReply(Integer commentId) {
        return this.baseMapper.getAllCourseCommentReply(commentId);
    }

    @Override
    public List<CourseCommentReplyVO> getUserBeReplyCourseComment(Integer uId) {
        return this.baseMapper.getUserBeReplyCourseComment(uId);
    }
}
