package com.xgxfd.moocback.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
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
public class CourseEvaluationVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer Id;

    /**
     * 课程编号
     */
    private Integer courseId;

    /**
     * 学生编号
     */
    private Integer uId;

    /**
     * 评价时间
     */
    private LocalDateTime time;

    /**
     * 评分：1~5
     */
    private Integer score;

    /**
     * 评价内容
     */
    private String content;

    /**
     * 评论状态：1-有效，2-无效
     */
    private String status;

    /**
     * 讲师是否回复：1-是，0-否
     */
    private String isReply;

    /**
     * 讲师编号
     */
    private Integer teaId;

    /**
     * 回复时间
     */
    private LocalDateTime replyTime;

    /**
     * 讲师回复内容
     */
    private String replyContent;


    //add

    private String username;

    private String courseName;

}
