package com.xgxfd.moocback.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author Xxz Wgl
 * @since 2019-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class StudyRecord implements Serializable {

    private static final long serialVersionUID = 1L;

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
     * 章节编号
     */
    private Integer sectionId;

    /**
     * 最近学习时间
     */
    private LocalDateTime latesTime;


}
