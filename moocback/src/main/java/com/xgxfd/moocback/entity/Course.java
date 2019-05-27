package com.xgxfd.moocback.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 课程表
 * </p>
 *
 * @author Xxz Wgl
 * @since 2019-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

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


}
