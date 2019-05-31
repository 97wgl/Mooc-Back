package com.xgxfd.moocback.mapper;

import com.xgxfd.moocback.entity.StudyRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xgxfd.moocback.vo.StudyRecordInfoVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Xxz Wgl
 * @since 2019-05-27
 */
public interface StudyRecordMapper extends BaseMapper<StudyRecord> {
    @Select("SELECT study_record.*,course_section.`name` section_name,course.`name` course_name from study_record LEFT JOIN course_section ON study_record.section_id = course_section.section_id AND study_record.u_id=#{userId} LEFT JOIN course ON study_record.course_id=course.course_id")
    List<StudyRecordInfoVO> getStudyRecordInfo(@Param("userId") String userId);

    @Select("SELECT study_record.*,course_section.`name` section_name,course.`name` course_name from study_record LEFT JOIN course_section ON study_record.section_id = course_section.section_id AND study_record.u_id=#{userId} AND study_record.course_id=#{courseId} LEFT JOIN course ON study_record.course_id=course.course_id")
    List<StudyRecordInfoVO> getStudyRecordInfo(@Param("userId") String userId, @Param("courseId") String courseId);
}
