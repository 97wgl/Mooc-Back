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
public class CourseSection implements Serializable {

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
     * 章节父级编号，根章节编号为0
     */
    private Integer parentId;

    /**
     * 章节名
     */
    private String name;

    /**
     * 视频路径
     */
    private String videoUrl;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 时长，单位秒(s)
     */
    private Integer time;


}
