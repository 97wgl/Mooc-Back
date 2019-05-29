package com.xgxfd.moocback.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xgxfd.moocback.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
