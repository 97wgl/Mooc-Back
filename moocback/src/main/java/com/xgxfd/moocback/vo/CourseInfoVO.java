package com.xgxfd.moocback.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 王桂林
 * @Title: CourseInfoVO
 * @ProjectName moocback
 * @Description: TODO
 * @date 2019/5/2915:45
 */
@Data
public class CourseInfoVO {
    /**
     * 课程编号
     */
    @TableId(value = "course_id", type = IdType.AUTO)
    private Integer courseId;

    /**
     * 课程名称
     */
    private String name;

    /**
     * 类别
     */
    private String classify;

    /**
     * 讲师编号
     */
    private Integer teaId;

    /**
     * 课程级别：1-初级，2-中级，3-高级
     */
    private String level;

    /**
     * 时长
     */
    private String time;

    /**
     * 课程描述
     */
    private String brief;

    /**
     * 学习人数
     */
    private Integer studyCount;

    /**
     * 权重
     */
    private Integer weight;

    /**
     * 课程展示图片
     */
    private String picture;

    /**
     * 课程发布时间
     */
    private LocalDateTime publishTime;

    /**
     * 审核状态:0-审核未通过,1-审核通过
     */
    private String status;

    /**
     * 课程评分
     */
    private String score;

}
