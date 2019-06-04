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
     * 好课推荐
     * @return
     */
    @Select("SELECT * from (SELECT course.* FROM course LEFT JOIN (SELECT course_id, AVG(score) avg_score from course_evaluation GROUP BY course_id) ev ON course.course_id=ev.course_id ORDER BY ev.avg_score DESC) a WHERE a.`status` = 1 LIMIT 5")
    List<Course> getGoodCourses();

    /**
     * 获取课程评分
     */
    @Select("SELECT course.*,AVG(score) score from(SELECT course.*,IFNULL(score,0)score from course left JOIN course_evaluation ON course.course_id = course_evaluation.course_id) a,course where a.course_id=course.course_id and a.course_id = #{courseId} GROUP BY course_id")
    CourseInfoVO getCourseInfoById(@Param("courseId") String courseId);

    /**
     * 按评分高低返回类型为classify的课程列表
     * @param classify
     * @return
     */
    @Select("SELECT * from (SELECT course.* FROM course LEFT JOIN (SELECT course_id, AVG(score) avg_score from course_evaluation GROUP BY course_id) ev ON course.course_id=ev.course_id ORDER BY ev.avg_score DESC) a WHERE a.`status` = 1 and classify=#{classify}")
    List<Course> getCourseInfoByClassify(@Param("classify") String classify);

    /**
     * 按评分高低返回课程列表
     * @return
     */
    @Select("SELECT * from (SELECT course.* FROM course LEFT JOIN (SELECT course_id, AVG(score) avg_score from course_evaluation GROUP BY course_id) ev ON course.course_id=ev.course_id ORDER BY ev.avg_score DESC) a WHERE a.`status` = 1")
    List<Course> getAllGoodCourses();

    /**
     * 修改学习人数
     * @param courseId
     * @return
     */
    @Select("UPDATE course SET study_count = (study_count + 1) WHERE course_id =#{courseId}")
    void updateStudyCount(@Param("courseId") Integer courseId);

}
