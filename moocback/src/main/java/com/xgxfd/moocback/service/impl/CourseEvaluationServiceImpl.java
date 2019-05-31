package com.xgxfd.moocback.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xgxfd.moocback.entity.CourseEvaluation;
import com.xgxfd.moocback.mapper.CourseEvaluationMapper;
import com.xgxfd.moocback.service.CourseEvaluationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xgxfd.moocback.vo.CourseEvaluationVO;
import javafx.scene.control.Pagination;
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
public class CourseEvaluationServiceImpl extends ServiceImpl<CourseEvaluationMapper, CourseEvaluation> implements CourseEvaluationService {


    @Override
    public IPage<CourseEvaluationVO> getCourseAllEvaluationVO(Page<CourseEvaluationVO> page,String courseId) {

        return this.baseMapper.getCourseEvaluationVO(page,courseId);
    }

    @Override
    public List<CourseEvaluationVO> getUserBeReplyCourseEvaluationVO(Integer uId) {
        return this.baseMapper.getUserBeReplyCourseEvaluationVO(uId);
    }
}
