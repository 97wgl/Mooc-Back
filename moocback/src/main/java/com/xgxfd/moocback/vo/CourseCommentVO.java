package com.xgxfd.moocback.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CourseCommentVO {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer Id;
    /**
     * 课程编号
     */
    private Integer courseId;

    private String courseName;
    /**
     * 章节编号
     */
    private Integer sectionId;

    /**
     * 用户编号
     */
    private Integer uId;

    private String username;

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

    List<CourseCommentReplyVO> courseCommentReplyVOList;

}
