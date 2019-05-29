package com.xgxfd.moocback.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xgxfd.moocback.entity.CourseEvaluation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xgxfd.moocback.vo.CourseEvaluationVO;
import javafx.scene.control.Pagination;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.hibernate.validator.constraints.EAN;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Xxz Wgl
 * @since 2019-05-27
 */
public interface CourseEvaluationMapper extends BaseMapper<CourseEvaluation> {

    /*
    查询课程的所有评价
     */

    @Select("select c.*,a.name as courseName,b.name as username from course a,user b,course_evaluation c where c.course_id = #{courseId} and a.course_id = c.course_id and b.u_id = c.u_id" )
    IPage<CourseEvaluationVO> getCourseEvaluationVO(Page page,@Param("courseId") String courseId);

}
