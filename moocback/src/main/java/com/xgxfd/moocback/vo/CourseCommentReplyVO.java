package com.xgxfd.moocback.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CourseCommentReplyVO {


    private Integer id; //回复编号

    /**
     * 留言编号
     */
    private Integer commentId;

    /**
     * 回复者编号
     */
    private Integer replyUId;

    private String replyUName;

    /**
     * 回复内容
     */
    private String content;

    /**
     * 回复时间
     */
    private LocalDateTime createTime;

    /**
     * 回复谁
     */
    private Integer replyToUId;

    private String replyToUName;

    private Integer sectionId;




}
