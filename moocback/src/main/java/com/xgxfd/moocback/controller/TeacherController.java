package com.xgxfd.moocback.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.imageio.plugins.common.I18N;
import com.xgxfd.moocback.entity.Course;
import com.xgxfd.moocback.entity.HostHolder;
import com.xgxfd.moocback.entity.Teacher;
import com.xgxfd.moocback.service.CourseService;
import com.xgxfd.moocback.service.TeacherService;
import com.xgxfd.moocback.util.CommonUtil;
import com.xgxfd.moocback.util.MailSender;
import com.xgxfd.moocback.vo.MessageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Xxz Wgl
 * @since 2019-05-27
 */
@Slf4j
@CrossOrigin(origins = "*",
        maxAge = 3600,
        methods = {RequestMethod.DELETE, RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT})
@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    TeacherService teacherService;

    @Autowired
    CourseService courseService;

    @Autowired
    HostHolder hostHolder;

    @PostMapping("/login")
    @ResponseBody
    public String userLogin(@RequestParam("username") String username,
                            @RequestParam("password") String password,
                            HttpServletResponse response) {
        Teacher teacher = teacherService.getOne(new QueryWrapper<Teacher>().eq("name", username).eq("pwd", CommonUtil.MD5(password)));
        MessageVO<Map<String, String>> messageVO;
        if (teacher != null) {//登录成功

            Cookie cookie = new Cookie("userInfo", username);
            Cookie cookie1 = new Cookie("type", "teacher");
            cookie.setPath("/");
            cookie1.setPath("/");
            cookie.setMaxAge(3600 * 24 * 5);
            cookie1.setMaxAge(3600 * 24 * 5);
            response.addCookie(cookie);
            response.addCookie(cookie1);

            hostHolder.setUser(teacher);//设置hostholder

            Map<String, String> map = new HashMap<>();
            map.put("userInfo", username);
            map.put("type", "teacher");
            map.put("id", teacher.getTeaId().toString());
            log.info("登录成功。 登录人：" + username + "登录时间：" + LocalDateTime.now());
            messageVO = new MessageVO<>(0, "登录成功", map);
        } else {
            log.error("登录失败。 登录人：" + username + "登录时间：" + LocalDateTime.now());
            messageVO = new MessageVO<>(-1, "用户名或密码错误", null);
        }
        return messageVO.getReturnResult(messageVO);
    }


    @GetMapping("famous")
    @ResponseBody
    public MessageVO<List<Teacher>> famousTeacherList() {
        QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<>();
        teacherQueryWrapper.last("limit 5");
        List<Teacher> list = teacherService.list(teacherQueryWrapper);
        MessageVO<List<Teacher>> messageVO = new MessageVO<>();
        if (list.size() == 0) {
            messageVO.setCode(-1);
            messageVO.setMsg("当前没有老师！");
        } else {
            messageVO.setCode(0);
            messageVO.setMsg("success");
            messageVO.setData(list);
        }
        return messageVO;
    }

    @GetMapping("info")
    @ResponseBody
    public MessageVO<Teacher> getTeacherInfoByid(@RequestParam("teacherId") String teacherId) {
        Teacher teacher = teacherService.getById(teacherId);
        MessageVO<Teacher> messageVO = new MessageVO<>();
        if (teacher == null) {
            messageVO.setCode(-1);
            messageVO.setMsg("查询不到id");
        } else {
            messageVO.setCode(0);
            messageVO.setMsg("success");
            teacher.setPwd("");  // 密码没必要传到前端
            messageVO.setData(teacher);
        }
        return messageVO;
    }

    @PutMapping("/info")
    @ResponseBody
    public String putTeacherInfo(@RequestBody Teacher teacher){

        Integer teaId = teacher.getTeaId();
        log.info("传入的teaId:"+teaId);
        Teacher tmp = teacherService.getById(teaId);
        MessageVO<Map<String, String>> messageVO;
        if(tmp != null){
            Boolean flag = teacherService.updateById(teacher);
            if(flag){
                Map<String, String> map = new HashMap<>();
                map.put("userInfo", teacher.getName());
                map.put("type", "teacher");
                map.put("id", String.valueOf(teaId));
                messageVO = new MessageVO<>(0,"教师信息更新成功",map);
            }else{
                messageVO = new MessageVO<>(-1,"教师信息更新失败",null);
            }
        }else {
            messageVO = new MessageVO<>(-1,"教师Id不存在",null);
        }
        return messageVO.getReturnResult(messageVO);
    }

    @PutMapping("/password")
    @ResponseBody
    public String putTeacherPassword(@RequestParam("teaId") Integer teaId,
                                     @RequestParam("oldPwd") String oldPwd,
                                     @RequestParam("newPwd") String newPwd){

        Teacher teacher = teacherService.getById(teaId);
        MessageVO<String> messageVO;
        if(teacher != null){
          Teacher tmp = teacherService.getOne(new QueryWrapper<Teacher>().eq("tea_id",teaId).eq("pwd",CommonUtil.MD5(oldPwd)));
          if(tmp != null){
              tmp.setPwd(CommonUtil.MD5(newPwd));
              Boolean flag = teacherService.updateById(tmp);
              if(flag){
                  messageVO = new MessageVO<>(0,"教师修改密码成功",null);
              }else{
                  messageVO = new MessageVO<>(-1,"修改密码失败",null);
              }
          }else {
              messageVO = new MessageVO<>(-1,"原密码错误",null);
          }
        }else {
            messageVO = new MessageVO<>(-1,"教师Id不存在",null);
        }
        return messageVO.getReturnResult(messageVO);
    }


    @PostMapping("/upload")
    @ResponseBody
    public MessageVO<String> upload(@RequestParam("file") MultipartFile file, @RequestParam("type") String type) {
        MessageVO<String> messageVO = new MessageVO<>();
        if (file.isEmpty()) {
            messageVO.setCode(-1);
            messageVO.setMsg("上传失败，请选择文件");
            return messageVO;
        }
        String fileName = file.getOriginalFilename();
        String filePath = "F:\\个人\\彭世维毕业设计\\MOOC\\Project\\Mooc-Back\\moocback\\src\\main\\resources\\static\\" + type + "\\";
        log.info(filePath);
        File dir = new File(filePath);
        if(!dir.exists()) {
            dir.mkdir();
        }
        File dest = new File(filePath + fileName);
        try {
            file.transferTo(dest);
            log.info("上传成功");
            messageVO.setCode(0);
            messageVO.setMsg("上传成功！");
        } catch (IOException e) {
            messageVO.setCode(-1);
            messageVO.setMsg("上传失败！" + e.getMessage());
            log.error(e.toString(), e);
        }
        return messageVO;
    }

    @PostMapping("/course")
    @ResponseBody
    public MessageVO<String> addCourseInfo(@RequestParam("picture") MultipartFile picture,
                                           @RequestParam("teacherId") Integer teacherId,
                                           @RequestParam("courseName") String courseName,
                                           @RequestParam("brief") String brief,
                                           @RequestParam("level") String level,
                                           @RequestParam("classify") String classify) {
        MessageVO<String> messageVO = new MessageVO<>();
        if (picture.isEmpty()) {
            messageVO.setCode(-1);
            messageVO.setMsg("未添加图片文件！");
            return messageVO;
        }
        String fileName = picture.getOriginalFilename();
        String filePath = "F:\\个人\\彭世维毕业设计\\MOOC\\Project\\Mooc-Back\\moocback\\src\\main\\resources\\static\\image\\";
        log.info(filePath);
        File dir = new File(filePath);
        if(!dir.exists()) {
            dir.mkdir();
        }
        String[] fileNameSplit = fileName.split("\\.");
        fileName = UUID.randomUUID() + "." + fileNameSplit[fileNameSplit.length - 1];
        File dest = new File(filePath + fileName);
        try {
            picture.transferTo(dest);
            log.info("图片存入成功！");
            messageVO.setCode(0);
            messageVO.setMsg("添加成功！");
        } catch (IOException e) {
            messageVO.setCode(-1);
            messageVO.setMsg("添加失败--图片存入异常！" + e.getMessage());
            log.error(e.toString(), e);
        }
        Course course = new Course();
        course.setBrief(brief);
        course.setClassify(classify);
        course.setLevel(level);
        course.setTeaId(teacherId);
        course.setName(courseName);
        course.setPicture("/image/" + fileName);
        courseService.save(course);
        return messageVO;
    }

    @GetMapping("course")
    @ResponseBody
    public MessageVO<List<Course>> courseListOfTeacher(@RequestParam("teacherId") String teacherId) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tea_id", teacherId);
        List<Course> courseList = courseService.list(queryWrapper);
        MessageVO<List<Course>> messageVO = new MessageVO<>();
        if (courseList.size() == 0) {
            messageVO.setCode(-1);
            messageVO.setMsg("当前老师没有课程！");
        } else {
            messageVO.setCode(0);
            messageVO.setMsg("success");
            messageVO.setData(courseList);
        }
        return messageVO;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String getAllTeacher(@RequestParam("page") Integer page,
                                @RequestParam("rows") Integer rows){
        Page<Teacher> teacherPage = new Page<>(page,rows);
        IPage<Teacher> teacherIPage1 = teacherService.page(teacherPage,new QueryWrapper<Teacher>().orderByDesc("status"));
       // IPage<Teacher> teacherIPage2 = teacherService.page(teacherPage,new QueryWrapper<Teacher>().eq("status","1"));
        List<Teacher> list = teacherIPage1.getRecords();
        MessageVO<List<Teacher>> messageVO;
        if(list.size() > 0){
            messageVO = new MessageVO<>(0,"获取教师列表成功",list);
        }else{
            messageVO = new MessageVO<>(-1,"获取教师列表失败",null);
        }
        return messageVO.getReturnResult(messageVO);

    }

    @PutMapping("/status")
    @ResponseBody
    public String putTeacherStatus(@RequestParam("teaId") Integer teaId){

        MessageVO<String> messageVO;
        Teacher teacher = teacherService.getById(teaId);
        if(teacher != null){
            teacher.setStatus("1");
            Boolean flag = teacherService.updateById(teacher);
            if(flag){
                messageVO = new MessageVO<>(0,"教师审核成功 可以正常发布课程",null);
            }else {
                messageVO = new MessageVO<>(-1,"教师审核失败",null);
            }
        }else{
            messageVO = new MessageVO<>(-1,"教师Id不存在",null);

        }
       return messageVO.getReturnResult(messageVO);
    }

}
