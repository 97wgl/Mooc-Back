package com.xgxfd.moocback.mapper;

import com.xgxfd.moocback.entity.CourseCommentReply;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xgxfd.moocback.vo.CourseCommentReplyVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Xxz Wgl
 * @since 2019-05-27
 */
public interface CourseCommentReplyMapper extends BaseMapper<CourseCommentReply> {


    /*
    查看所有的课程章节留言的回复
     */

    @Select("select c.*,d.name as replyToUName from(SELECT a.*,b.name as replyUName FROM course_comment_reply a left join user b on a.reply_u_id = b.u_id where comment_id = #{commentId}) c left join user d on c.reply_to_u_id = d.u_id ORDER BY c.create_time desc")
    List<CourseCommentReplyVO> getAllCourseCommentReply(Integer commentId);


    /*
      获取 用户被回复的留言回复 时间倒序
     */
    @Select("select c.*,d.name as replyToUName from(select a.*,b.name as replyUName,e.section_id from course_comment_reply a,user b,course_comment e where a.reply_to_u_id = #{uId} and a.reply_u_id = b.u_id and a.comment_id = e.id ) c ,user d where c.reply_to_u_id = d.u_id order by create_time desc")
    List<CourseCommentReplyVO> getUserBeReplyCourseComment (Integer uId);
}
