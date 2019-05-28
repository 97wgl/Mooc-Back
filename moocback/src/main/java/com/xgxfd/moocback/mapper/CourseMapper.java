package com.xgxfd.moocback.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xgxfd.moocback.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
     * 查询评分前5的课程
     * @return
     */
    @Select("SELECT course.* FROM course,(SELECT course_id, AVG(score) avg_score from course_evaluation GROUP BY course_id) ev WHERE course.course_id=ev.course_id ORDER BY ev.avg_score LIMIT 5")
    List<Course> getCourseInfo();
}
