package com.xgxfd.moocback.mapper;

import com.xgxfd.moocback.entity.CourseComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xgxfd.moocback.vo.CourseCommentVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Xxz Wgl
 * @since 2019-05-27
 */
public interface CourseCommentMapper extends BaseMapper<CourseComment> {

/*
查看当前课程 的章节 的所有留言
 */
    @Select("select a.*,b.name as username,c.name as courseName from course_comment a,user b, course c where a.course_id = #{courseId} and a.section_id = #{sectionId} and a.course_id = c.course_id and a.u_id = b.u_id ORDER BY a.create_time desc")
    List<CourseCommentVO> getAllCourseComment(Integer courseId,Integer sectionId);

    /*
    查看当前课程的所有留言
     */
    @Select("select a.*,b.name as username,c.name as courseName from course_comment a,user b, course c where a.course_id = #{courseId} and a.course_id = c.course_id and a.u_id = b.u_id ORDER BY a.create_time desc")
    List<CourseCommentVO> getCourseComment(Integer courseId);

}
