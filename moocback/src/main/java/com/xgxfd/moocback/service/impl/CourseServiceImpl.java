package com.xgxfd.moocback.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xgxfd.moocback.entity.Course;
import com.xgxfd.moocback.mapper.CourseMapper;
import com.xgxfd.moocback.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 课程表 服务实现类
 * </p>
 *
 * @author Xxz Wgl
 * @since 2019-05-27
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Override
    public List<Course> getCourseInfo() {
        return this.baseMapper.getCourseInfo();
    }
}
