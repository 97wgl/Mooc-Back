package com.xgxfd.moocback.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xgxfd.moocback.entity.CourseEvaluation;
import com.xgxfd.moocback.entity.HostHolder;
import com.xgxfd.moocback.entity.User;
import com.xgxfd.moocback.service.CourseEvaluationService;
import com.xgxfd.moocback.vo.MessageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.print.attribute.standard.Media;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Xxz Wgl
 * @since 2019-05-27
 */
@Slf4j
@Controller
@RequestMapping("/course-evaluation")
public class CourseEvaluationController {

    @Autowired
    CourseEvaluationService courseEvaluationService;

    @Autowired
    HostHolder hostHolder;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String getCourseEvaluation(@RequestParam("page") int page,
                                      @RequestParam("limit") int limit) {

        Page<CourseEvaluation> courseEvaluationPage = new Page<>(page, limit);
        IPage<CourseEvaluation> courseEvaluationIPage = courseEvaluationService.page(courseEvaluationPage,new QueryWrapper<CourseEvaluation>().orderByDesc("time"));
        List<CourseEvaluation> list = courseEvaluationIPage.getRecords();
        MessageVO<List<CourseEvaluation>> messageVO = new MessageVO<>(0, "当前所有评价", list);
        return messageVO.getReturnResult(messageVO);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String postCourseEvaluation(@RequestParam("course_id") int course_id,
                                       @RequestParam("u_id") int u_id,
                                       @RequestParam("score") int score,
                                       @RequestParam("content") String content){

        CourseEvaluation courseEvaluation = new CourseEvaluation();
        courseEvaluation.setCourseId(course_id);
        courseEvaluation.setScore(score);
        courseEvaluation.setContent(content);

        //log.info("当前线程:"+Thread.currentThread());
        //User user = (User) hostHolder.getUser();
        courseEvaluation.setUId(u_id);
        courseEvaluation.setTime(LocalDateTime.now());
        courseEvaluation.setStatus("1");
        courseEvaluation.setIsReply("0");
        Boolean flag = courseEvaluationService.save(courseEvaluation);
        MessageVO<String> messageVO;

        if(flag){
            log.info("用户评价课程成功" );
            messageVO = new MessageVO<String>(0,"课程评价成功",null);
        }else{
            log.error("用户评价课程失败"+"评价时间" + LocalDateTime.now());
            messageVO = new MessageVO<String>(0,"课程评价失败",null);
        }
        return messageVO.getReturnResult(messageVO);

    }


    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public String putCourseEvaluation(@RequestParam("id") int id,
                                      @RequestParam("tea_id") int tea_id,
                                      @RequestParam("reply_content") String reply_content){


        CourseEvaluation courseEvaluation = courseEvaluationService.getById(id);
        MessageVO<String> messageVO;
        if(courseEvaluation == null){ //课程评价不存在
            log.error("教师回复课程评价失败 原因：评价不存在 ");
            messageVO = new MessageVO<String>(-1,"评价不存在",null);
        }else if(courseEvaluation.getIsReply().equals("1")){
            log.error("教师回复课程评价失败 原因：已回复该课程评价 ");
            messageVO = new MessageVO<String>(-1,"已回复该评价",null);
        }
        else{
            courseEvaluation.setIsReply("1");
            courseEvaluation.setReplyTime(LocalDateTime.now());
            courseEvaluation.setTeaId(tea_id);
            courseEvaluation.setReplyContent(reply_content);
            Boolean flag = courseEvaluationService.updateById(courseEvaluation);
            if(flag){//教师回复课程评价成功
                log.info("教师回复课程评价成功");
                messageVO = new MessageVO<String>(0,"教师回复课程评价成功",null);
            }else{
                log.error("更新课程评价失败");
                messageVO = new MessageVO<String>(-1,"更新课程评价失败",null);
            }
        }
      return  messageVO.getReturnResult(messageVO);
    }
}
