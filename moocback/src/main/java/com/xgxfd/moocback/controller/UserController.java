package com.xgxfd.moocback.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.xgxfd.moocback.entity.CourseCommentReply;
import com.xgxfd.moocback.entity.HostHolder;
import com.xgxfd.moocback.entity.Teacher;
import com.xgxfd.moocback.entity.User;
import com.xgxfd.moocback.service.CourseCommentReplyService;
import com.xgxfd.moocback.service.CourseEvaluationService;
import com.xgxfd.moocback.service.TeacherService;
import com.xgxfd.moocback.service.UserService;
import com.xgxfd.moocback.util.CommonUtil;
import com.xgxfd.moocback.util.FileUpload;
import com.xgxfd.moocback.util.MailSender;
import com.xgxfd.moocback.vo.CourseCommentReplyVO;
import com.xgxfd.moocback.vo.CourseEvaluationVO;
import com.xgxfd.moocback.vo.MessageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Xxz Wgl
 * @since 2019-05-27
 */

@Slf4j
/*@CrossOrigin(origins = "http://192.168.1.108",
             maxAge = 3600,
             allowCredentials = "true",
             methods = {RequestMethod.DELETE,RequestMethod.POST,RequestMethod.GET,RequestMethod.PUT})*/
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    MailSender mailSender;

    @Autowired
    TeacherService teacherService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    CourseEvaluationService courseEvaluationService;

    @Autowired
    CourseCommentReplyService courseCommentReplyService;

    private StringBuffer valid;


    @PostMapping("/regist")
    @ResponseBody
    public String teacherRegist(@RequestParam("email") String email,
                                @RequestParam("validCode") String validCode,
                                @RequestParam("username") String username,
                                @RequestParam("password") String password){
        MessageVO<String> messageVO ;
        if(!validCode.equals(valid.toString())){//验证码错误
            messageVO = new MessageVO<String>(-1,"验证码填写错误",null);
        } else{
            User user = userService.getOne(new QueryWrapper<User>().eq("name",username));
            if(user != null){//用户名已存在
                messageVO = new MessageVO<String>(-1,"用户名已存在",null);
            }else{//注册用户
                user = new User(); //对象为空 先new
                user.setName(username);
                user.setEmail(email);
                user.setRegistDate(LocalDateTime.now());
                user.setPwd(CommonUtil.MD5(password));
                Boolean flag = userService.save(user);
                if(flag){//注册成功
                    log.info("用户注册成功。注册人：" + username + "注册时间:" + LocalDateTime.now());
                    messageVO = new MessageVO<String>(0,"注册成功",null);
                }else{//注册失败
                    messageVO = new MessageVO<String>(-1,"注册失败",null);
                }
            }
        }
        return messageVO.getReturnResult(messageVO);
    }

    @PostMapping("/regist/valid")
    @ResponseBody
    public String emailSendValid(@RequestParam("email") String email){

        MessageVO<StringBuffer> messageVO;
        User user = userService.getOne(new QueryWrapper<User>().eq("email",email));
        if(user != null){ //邮箱已存在
            messageVO = new MessageVO<>(-1,"邮箱已存在",null);
        }else {
            valid = new CommonUtil().registValid(valid);
            Map<String, Object> map = new HashMap<>();
            map.put("validCode", valid.toString());
            Boolean flag = mailSender.sendWithHTMLTemplate(email, "MOOC注册验证码", "emailTemplate.ftl", map);

            if (flag) {
                messageVO = new MessageVO<StringBuffer>(0, "发送验证码成功", null);
            } else {
                messageVO = new MessageVO<StringBuffer>(-1, "发送验证码失败", null);
            }
        }
        return messageVO.getReturnResult(messageVO);
    }

    @PostMapping("/login")
    @ResponseBody
    public String userLogin(@RequestParam("username") String username,
                            @RequestParam("password") String password,
                            HttpServletResponse response,
                            HttpServletRequest request){
        MessageVO<Map<String,String>> messageVO;
        User tmp = userService.getOne(new QueryWrapper<User>().eq("name",username));
        if(tmp != null) {
            User user = userService.getOne(new QueryWrapper<User>().eq("name", username).eq("pwd", CommonUtil.MD5(password)));

            if (user != null) {//登录成功  保存当前用户

                Cookie cookie = new Cookie("userInfo", username);
                Cookie cookie1 = new Cookie("type", "user");
                cookie.setPath("/");
                cookie1.setPath("/");
                cookie.setMaxAge(3600 * 24 * 5);
                cookie1.setMaxAge(3600 * 24 * 5);
                response.addCookie(cookie);
                response.addCookie(cookie1);

           /* response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
            response.setHeader("Access-Control-Allow-Headers", "Set-Cookie,Timestamp,Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,token,Access-Control-Allow-Headers");
            response.addHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
            response.setCharacterEncoding("UTF-8");*/
                // response.setContentType("application/json");

                log.info("当前线程:" + Thread.currentThread());
                hostHolder.setUser(user);//设置hostholder

                Map<String, String> map = new HashMap<>();
                map.put("userInfo", username);
                if (user.getIsTeacher().equals("0")) { // 0初始状态
                    map.put("type", "user");
                } else {
                    map.put("type", "teacher");
                }
                map.put("id", user.getUId().toString());
                log.info("登录成功。 登录人：" + username + "登录时间：" + LocalDateTime.now());
                messageVO = new MessageVO<>(0, "登录成功", map);
            } else {
                log.error("登录失败。 登录人：" + username + "登录时间：" + LocalDateTime.now());
                messageVO = new MessageVO<>(-1, "用户名或密码错误", null);
            }
        }else{
            messageVO = new MessageVO<>(-1, "用户名不存在", null);

        }
        return  messageVO.getReturnResult(messageVO);
    }

    //@RequestMapping(method = RequestMethod.GET)
    @GetMapping("")
    @ResponseBody
    public String getUser(@RequestParam("u_id") Integer u_id){
        User user = userService.getById(u_id);
        MessageVO<User> messageVO;
        if(user != null){
            user.setPwd(""); //密码没必要传回去
            if(user.getIsTeacher().equals("0")){
                user.setIsTeacher("user");
            }else {
                user.setIsTeacher("teacher");
            }
            messageVO = new MessageVO<>(0,"获取用户成功",user);
        }else {
            messageVO = new MessageVO<>(-1,"获取用户失败 用户id不存在",null);
        }
        return messageVO.getReturnResult(messageVO);
    }

    @PutMapping("/info")
    @ResponseBody
    public String putUser(@RequestParam("u_id") int u_id,
                          @RequestParam("name") String name,
                          @RequestParam("sex") String sex,
                          @RequestParam("tel") String tel,
                          @RequestParam("email") String email,
                          @RequestParam("remark") String remark){

        User user = userService.getById(u_id);
        MessageVO<Map<String,String>>  messageVO;
        if(user != null){
            user.setName(name);
            user.setTel(tel);
            user.setSex(sex);
            user.setEmail(email);
            user.setRemark(remark);
            Boolean flag = userService.updateById(user);
            if(user.getIsTeacher().equals("1")){ //是教师 需要同步修改 教师信息
                Teacher teacher =teacherService.getOne(new QueryWrapper<Teacher>().eq("tea_id",user.getUId()));
                teacher.setName(name);
                teacher.setTel(tel);
                teacher.setSex(sex);
                teacher.setEmail(email);
                teacher.setRemark(remark);
                Boolean flag2 = teacherService.update(new UpdateWrapper<Teacher>().set("name",name).set("tel",tel).set("sex",sex).set("email",email).set("remark",remark).eq("tea_id",user.getUId()));
                log.info("同步更新教师信息 ："+ flag2);
            }

            if(flag) {
                Map<String, String> map = new HashMap<>();
                map.put("userInfo", name);
                if(user.getIsTeacher().equals("0")) {
                    map.put("type", "user");
                }else{
                    map.put("type", "teacher");
                }
                map.put("id", String.valueOf(u_id));
                messageVO = new MessageVO<>(0, "个人信息修改成功", map);
            }
            else{
                messageVO = new MessageVO<>(-1, "个人信息修改失败", null);

            }
        }else{
          messageVO = new MessageVO<>(-1,"u_id异常 用户不存在",null);
        }
       return messageVO.getReturnResult(messageVO);
    }

    @PutMapping("/password")
    @ResponseBody
    public String putUser(@RequestParam("uId") Integer uId,
                          @RequestParam("oldPwd") String oldPwd,
                          @RequestParam("newPwd") String newPwd){

        User user = userService.getById(uId);
        MessageVO<String> messageVO;
        if(user != null){
            User tmp = userService.getOne(new QueryWrapper<User>().eq("pwd",CommonUtil.MD5(oldPwd)).eq("u_id",uId));
            if(tmp != null){

                tmp.setPwd(CommonUtil.MD5(newPwd));
                Boolean flag = userService.updateById(tmp);
                if(flag){
                    messageVO = new MessageVO<String>(0,"用户密码更新成功",null);
                }else{
                    messageVO = new MessageVO<String>(-1,"用户密码更新错误",null);
                }

            }else{
                messageVO = new MessageVO<String>(-1,"原密码错误",null);
            }
        }else{
            messageVO = new MessageVO<String>(-1,"用户Id不存在",null);
        }
       return messageVO.getReturnResult(messageVO);
    }

    @PostMapping("apply")
    @ResponseBody
    public MessageVO<String> applyTeacher(@RequestParam("userId") Integer userId,
                                          @RequestParam("position") String position,
                                          @RequestParam("organization") String organization,
                                          @RequestParam("applyMaterial") MultipartFile[] applyMaterials,
                                          HttpServletRequest request) {
        MessageVO<String> messageVO = new MessageVO<>();
        User user = userService.getById(userId);
        Teacher teacher = new Teacher();
        teacher.setTeaId(userId);
        teacher.setPwd(user.getPwd());
        teacher.setEmail(user.getEmail());
        teacher.setHeadImg(user.getHeadImg());
        teacher.setOrgnization(organization);
        teacher.setPosition(position);
        teacher.setName(user.getName());
        teacher.setSex(user.getSex());
        teacher.setTel(user.getTel());
        teacher.setRemark(user.getRemark());
        log.info("" + applyMaterials.length);
        List<String> fileList = new ArrayList<>();
        List<MultipartFile> files = ((MultipartHttpServletRequest)request).getFiles("applyMaterial");
        for (MultipartFile multipartFile: files) {
            MessageVO<String> message = new FileUpload().upload(multipartFile, "material");
            String fileName;
            if (message.getCode() == -1) {
                log.info("save failure");
                messageVO.setCode(-1);
                messageVO.setMsg("save file failure!");
                return messageVO;
            } else {
                log.info("success");
                fileName = message.getData();
            }
            fileList.add("/material/" + fileName);
        }
        teacher.setApplicationMaterial(String.join(";", fileList));
        teacher.setTeaId(userId);
        teacherService.save(teacher);
        messageVO.setCode(0);
        messageVO.setMsg("success");
        messageVO.setData("提交成功！");
        return messageVO;
    }

    @GetMapping("/course-evaluation")
    @ResponseBody
    public String getUserCourseEvaluation(@RequestParam("uId") Integer uId){

        List<CourseEvaluationVO> list = courseEvaluationService.getUserBeReplyCourseEvaluationVO(uId);
        MessageVO<List<CourseEvaluationVO>> messageVO;
        if(list.size() > 0){
            messageVO = new MessageVO<>(0,"获取用户被回复的评价成功",list);
        }else {
            messageVO = new MessageVO<>(-1,"获取用户被回复的评价失败",null);
        }
        return messageVO.getReturnResult(messageVO);
    }

    @GetMapping("/course-comment-reply")
    @ResponseBody
    public  String getUserCourseCommentReply(@RequestParam("uId") Integer uId){

        List<CourseCommentReplyVO> list = courseCommentReplyService.getUserBeReplyCourseComment(uId);
        MessageVO<List<CourseCommentReplyVO>> messageVO;
        if(list.size() > 0){
            messageVO = new MessageVO<>(0,"获取用户被回复的留言回复成功",list);
        }else {
            messageVO = new MessageVO<>(-1,"获取用户被回复的留言回复失败",null);
        }
        return messageVO.getReturnResult(messageVO);
    }
}


