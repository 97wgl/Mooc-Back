package com.xgxfd.moocback.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
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
public class CourseComment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 课程编号
     */
    private Integer courseId;

    /**
     * 章节编号
     */
    private Integer sectionId;

    /**
     * 用户编号
     */
    private Integer uId;

    /**
     * 留言内容
     */
    private String content;

    /**
     * 留言时间
     */
    private LocalDateTime createTime;

    /**
     * 留言状态
     */
    private String status;


}
