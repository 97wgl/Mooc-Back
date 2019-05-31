package com.xgxfd.moocback.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 王桂林
 * @Title: CourseSectionVO
 * @ProjectName moocback
 * @Description: TODO
 * @date 2019/5/3115:12
 */
@Data
public class CourseSectionVO {

    /**
     * 课程编号
     */
    private Integer courseId;

    /**
     * 章节编号
     */
    @TableId(value = "section_id", type = IdType.AUTO)
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
     * 课程名
     */
    private String courseName;

    /**
     * 章节名
     */
    private String sectionName;

    /**
     * 时长，单位秒(s)
     */
    private Integer time;
}
