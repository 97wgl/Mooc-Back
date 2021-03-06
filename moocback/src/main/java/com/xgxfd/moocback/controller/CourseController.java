package com.xgxfd.moocback.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xgxfd.moocback.entity.Course;
import com.xgxfd.moocback.service.CourseService;
import com.xgxfd.moocback.vo.CourseInfoVO;
import com.xgxfd.moocback.vo.MessageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程表 前端控制器
 * </p>
 *
 * @author Wgl
 * @since 2019-05-27
 */
@Slf4j
@Controller
@RequestMapping("/course")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseController {
    @Autowired
    CourseService courseService;

    /**
     * 权重前5
     * @return
     */
    @GetMapping("carousel")
    @ResponseBody
    public MessageVO<List<Course>> carouselList() {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("weight").eq("status", 1).last("limit 5");
        List<Course> list = courseService.list(queryWrapper);
        MessageVO<List<Course>> messageVO = new MessageVO<>();
        if (list.size() == 0) {
            messageVO.setCode(-1);
            messageVO.setMsg("查询为空!");
        } else {
            messageVO.setCode(0);
            messageVO.setMsg("success");
            messageVO.setData(list);
        }
        return messageVO;
    }

    /**
     * 评分前5
     * @return
     */
    @GetMapping("good")
    @ResponseBody
    public MessageVO<List<Course>> goodCourses() {
        List<Course> list = courseService.getGoodCourses();
        MessageVO<List<Course>> messageVO = new MessageVO<>();
        if (list.size() == 0) {
            messageVO.setCode(-1);
            messageVO.setMsg("查询不到数据！");
        } else {
            messageVO.setCode(0);
            messageVO.setMsg("success");
            messageVO.setData(list);
        }
        return messageVO;
    }

    @GetMapping("classify")
    @ResponseBody
    public MessageVO<List<Course>> findOfClassify(@RequestParam("classify") String classify) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("classify", classify);
        List<Course> list = courseService.list(queryWrapper);
        MessageVO<List<Course>> messageVO = new MessageVO<>();
        if (list.size() == 0) {
            messageVO.setCode(-1);
            messageVO.setMsg("当前分类没有数据！");
            log.info("没有" + classify + "类的课程！");
        } else {
            messageVO.setCode(0);
            messageVO.setMsg("success");
            messageVO.setData(list);
            log.info(classify + "类别下有" + list.size() + "门课程！");
        }
        return messageVO;
    }

    @GetMapping("all")
    @ResponseBody
    public MessageVO<List<Course>> allCourse() {
        List<Course> list = courseService.list();
        MessageVO<List<Course>> messageVO = new MessageVO<>();
        if (list.size() == 0) {
            messageVO.setCode(-1);
            messageVO.setMsg("没有数据！");
            log.info("没有课程！");
        } else {
            messageVO.setCode(0);
            messageVO.setMsg("success");
            messageVO.setData(list);
            log.info("共有" + list.size() + "门课程！");
        }
        return messageVO;
    }

    /**
     * 分页显示课程，每页显示12门课
     * @param page
     * @return
     */
    @GetMapping("page/{page}")
    @ResponseBody
    public MessageVO<Map<String, Object>> allCoursePage(@PathVariable("page") int page) {
        Page<Course> coursePage = new Page<>(page, 12);
        IPage<Course> courseIPage = courseService.page(coursePage);
        List<Course> courseList = courseIPage.getRecords();
        long totalCourse = courseIPage.getTotal();
        Map<String, Object> map = new HashMap<>();
        map.put("total", totalCourse);
        map.put("list", courseList);
        MessageVO<Map<String, Object>> messageVO = new MessageVO<>();
        if (courseList.size() == 0) {
            messageVO.setCode(-1);
            messageVO.setMsg("页面参数有误！查询课程失败！");
            messageVO.setData(map);
        } else {
            messageVO.setCode(0);
            messageVO.setMsg("success!");
            messageVO.setData(map);
        }
        return messageVO;
    }

    @GetMapping("list")
    @ResponseBody
    public MessageVO<List<Course>> CourseList(@RequestParam(value = "classify", required = false) String classify, @RequestParam(value = "tag", required = false) int tag) {
        List<Course> list;
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", 1);
        if (classify != null) {
            queryWrapper.eq("classify", classify);
        } else {
            classify = "全部";
        }
        if (tag == 1) {  // 最新
            list = courseService.list(queryWrapper);
            queryWrapper.orderByDesc("publish_time");
            log.info("获取最新课程...");
        } else { // 最热
            log.info("获取最热课程！");
            if (classify == "全部") {
                list = courseService.getAllGoodCourses();
            } else {
                list = courseService.getCourseInfoByClassify(classify);
            }
        }
        MessageVO<List<Course>> messageVO = new MessageVO<>();
        if (list.size() == 0) {
            messageVO.setCode(-1);
            messageVO.setMsg("data empty");
            log.info("没有" + classify + "类的课程！");
        } else {
            messageVO.setCode(0);
            messageVO.setMsg("success");
            messageVO.setData(list);
            log.info(classify + "类别下有" + list.size() + "门课程！");
        }
        return messageVO;
    }

    @GetMapping("detail")
    @ResponseBody
    public MessageVO<CourseInfoVO> courseDetail(@RequestParam("courseId") String courseId) {
        CourseInfoVO courseInfo = courseService.getCourseInfoById(courseId);
        MessageVO<CourseInfoVO> messageVO = new MessageVO<>();
        if (courseInfo == null) {
            messageVO.setCode(-1);
            messageVO.setMsg("不存在该课程！");
            log.info("没有编号为" + courseId + "的课程！");
        } else {
            messageVO.setCode(0);
            messageVO.setMsg("success");
            messageVO.setData(courseInfo);
        }
        return messageVO;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String getAllCourse(@RequestParam("page") Integer page,
                               @RequestParam("rows") Integer rows){

        Page<Course> coursePage = new Page<>(page,rows);
        IPage<Course> courseIPage = courseService.page(coursePage,new QueryWrapper<Course>().orderByAsc("status"));
        List<Course> list = courseIPage.getRecords();
        MessageVO<List<Course>> messageVO;
        if(list.size() > 0){
            messageVO = new MessageVO<>(0,"获取课程列表成功",list);
        }else {
            messageVO = new MessageVO<>(-1,"获取课程列表失败",null);
        }
        return messageVO.getReturnResult(messageVO);

    }

    @PutMapping("/status")
    @ResponseBody
    public String putCourseStatus(@RequestParam("courseId") Integer courseId,
                                  @RequestParam("res") String res){
        MessageVO<String> messageVO;
        Course course = courseService.getById(courseId);
        if(course != null){
            course.setStatus(res);
            Boolean flag = courseService.updateById(course);
            if(flag){
                messageVO = new MessageVO<>(0,"课程审核成功",null);
            }else {
                messageVO = new MessageVO<>(-1,"课程审核失败",null);
            }
        }else {
            messageVO = new MessageVO<>(-1,"课程Id不存在",null);
        }
        return messageVO.getReturnResult(messageVO);
    }


}
