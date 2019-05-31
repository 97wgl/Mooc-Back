package com.xgxfd.moocback.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xgxfd.moocback.entity.CourseEvaluation;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xgxfd.moocback.vo.CourseEvaluationVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Xxz Wgl
 * @since 2019-05-27
 */
public interface CourseEvaluationService extends IService<CourseEvaluation> {

    IPage<CourseEvaluationVO> getCourseAllEvaluationVO(Page<CourseEvaluationVO> page,String courseId);

    List<CourseEvaluationVO> getUserBeReplyCourseEvaluationVO(Integer uId);
}
