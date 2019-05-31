package com.xgxfd.moocback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xgxfd.moocback.entity.Course;
import com.xgxfd.moocback.mapper.CourseMapper;
import com.xgxfd.moocback.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xgxfd.moocback.vo.CourseInfoVO;
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
    public List<Course> getCourseInfo(QueryWrapper<Course> queryWrapper) {
        return this.baseMapper.getCourseInfo(queryWrapper);
    }

    @Override
    public CourseInfoVO getCourseInfoById(String courseId) {
        return this.baseMapper.getCourseInfoById(courseId);
    }

    @Override
    public List<Course> getCourseInfoByClassify(String classify) {
        return this.baseMapper.getCourseInfoByClassify(classify);
    }

    @Override
    public void updateStudyCount(Integer courseId) {
        this.baseMapper.updateStudyCount(courseId);
    }
}
