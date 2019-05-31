package com.xgxfd.moocback.service.impl;

import com.xgxfd.moocback.entity.CourseComment;
import com.xgxfd.moocback.mapper.CourseCommentMapper;
import com.xgxfd.moocback.mapper.CourseCommentReplyMapper;
import com.xgxfd.moocback.service.CourseCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xgxfd.moocback.vo.CourseCommentReplyVO;
import com.xgxfd.moocback.vo.CourseCommentVO;
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
public class CourseCommentServiceImpl extends ServiceImpl<CourseCommentMapper, CourseComment> implements CourseCommentService {

    @Autowired
    CourseCommentReplyMapper courseCommentReplyMapper;

    @Override
    public List<CourseCommentVO> getAllCourseComment(Integer courseId,Integer sectionId) {
        List<CourseCommentVO> courseCommentVOList = this.baseMapper.getAllCourseComment(courseId,sectionId);

        for (int i = 0; i < courseCommentVOList.size() ; i++) {
            int commentId = courseCommentVOList.get(i).getId();
            List<CourseCommentReplyVO> list = courseCommentReplyMapper.getAllCourseCommentReply(commentId);
            courseCommentVOList.get(i).setCourseCommentReplyVOList(list);
        }

        return courseCommentVOList;
    }

    @Override
    public List<CourseCommentVO> getCourseComment(Integer courseId) {
        List<CourseCommentVO> courseCommentVOList = this.baseMapper.getCourseComment(courseId);

        for (int i = 0; i < courseCommentVOList.size() ; i++) {
            int commentId = courseCommentVOList.get(i).getId();
            List<CourseCommentReplyVO> list = courseCommentReplyMapper.getAllCourseCommentReply(commentId);
            courseCommentVOList.get(i).setCourseCommentReplyVOList(list);
        }
        return courseCommentVOList;
    }
}
