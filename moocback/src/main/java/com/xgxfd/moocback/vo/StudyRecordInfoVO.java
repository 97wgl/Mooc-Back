package com.xgxfd.moocback.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 王桂林
 * @Title: StudyRecordInfoVO
 * @ProjectName moocback
 * @Description: TODO
 * @date 2019/5/3116:00
 */
@Data
public class StudyRecordInfoVO {
    /**
     * 学习记录编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 学生编号
     */
    private Integer uId;

    /**
     * 课程编号
     */
    private Integer courseId;

    /**
     * 课程名
     */
    private String courseName;

    /**
     * 章节编号
     */
    private Integer sectionId;

    /**
     * 章节名
     */
    private String sectionName;

    /**
     * 最近学习时间
     */
    private LocalDateTime latesTime;
}
