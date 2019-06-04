package com.xgxfd.moocback.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xgxfd.moocback.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xgxfd.moocback.vo.CourseInfoVO;

import java.util.List;

/**
 * <p>
 * 课程表 服务类
 * </p>
 *
 * @author Xxz Wgl
 * @since 2019-05-27
 */
public interface CourseService extends IService<Course> {
    List<Course> getCourseInfo(QueryWrapper<Course> queryWrapper);

    CourseInfoVO getCourseInfoById(String courseId);

    List<Course> getCourseInfoByClassify(String classify);

    void updateStudyCount(Integer courseId);

    List<Course> getGoodCourses();
}
