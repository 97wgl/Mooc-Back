package com.xgxfd.moocback.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xgxfd.moocback.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xgxfd.moocback.vo.CourseInfoVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 课程表 Mapper 接口
 * </p>
 *
 * @author Xxz Wgl
 * @since 2019-05-27
 */
public interface CourseMapper extends BaseMapper<Course> {

    /**
     * 按评分高低返回课程列表
     * @return
     */
    @Select("SELECT course.* FROM course LEFT JOIN (SELECT course_id, AVG(score) avg_score from course_evaluation GROUP BY course_id) ev ON course.course_id=ev.course_id ORDER BY ev.avg_score DESC")
    List<Course> getCourseInfo(QueryWrapper<Course> queryWrapper);

    /**
     * 获取课程评分
     */
    @Select("SELECT course.*, ev.score FROM course, (SELECT course_id, AVG(score) score FROM course_evaluation WHERE course_id= #{courseId} GROUP BY course_id) ev WHERE ev.course_id = course.course_id")
    CourseInfoVO getCourseInfoById(@Param("courseId") String courseId);

    /**
     * 按评分高低返回类型为classify的课程列表
     * @param classify
     * @return
     */
    @Select("SELECT * FROM (SELECT course.* FROM course  LEFT JOIN (SELECT course_id, AVG(score) avg_score from course_evaluation GROUP BY course_id) ev ON course.course_id=ev.course_id ORDER BY ev.avg_score DESC)a WHERE a.classify=#{classify}")
    List<Course> getCourseInfoByClassify(@Param("classify") String classify);
}
