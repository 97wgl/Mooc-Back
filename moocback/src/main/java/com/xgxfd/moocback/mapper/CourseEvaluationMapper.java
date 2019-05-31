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

    @Select("select e.*,IFNULL(d.name,'') as teacherName from(select c.*,a.name as courseName,b.name as username   from course a,user b,course_evaluation c  where c.course_id = #{courseId} and a.course_id = c.course_id and b.u_id = c.u_id) e left join teacher  d on e.tea_id = d.tea_id ORDER BY e.is_reply" )
    IPage<CourseEvaluationVO> getCourseEvaluationVO(Page page,@Param("courseId") String courseId);


   /*
    查询教师相关的课程
    */


   /*
      获取用户 被回复的评价
    */
   @Select("select a.*,b.name as courseName,c.name as username,d.name as teacherName from course_evaluation a,course b,user c,teacher d where a.u_id = #{uId} and a.is_reply = 1 and a.course_id = b.course_id and a.u_id = c.u_id and a.tea_id = d.tea_id order by reply_time desc")
    List<CourseEvaluationVO> getUserBeReplyCourseEvaluationVO(Integer uId);

}
