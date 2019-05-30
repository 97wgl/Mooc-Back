package com.xgxfd.moocback.service;

import com.xgxfd.moocback.entity.CourseComment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xgxfd.moocback.vo.CourseCommentVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Xxz Wgl
 * @since 2019-05-27
 */
public interface CourseCommentService extends IService<CourseComment> {

    List<CourseCommentVO> getAllCourseComment(Integer courseId,Integer sectionId);
}
