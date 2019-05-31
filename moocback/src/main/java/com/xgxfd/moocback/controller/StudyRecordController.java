package com.xgxfd.moocback.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xgxfd.moocback.entity.StudyRecord;
import com.xgxfd.moocback.service.StudyRecordService;
import com.xgxfd.moocback.vo.MessageVO;
import com.xgxfd.moocback.vo.StudyRecordInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

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
@RequestMapping("/study-record")
public class StudyRecordController {

    @Autowired
    StudyRecordService studyRecordService;

    /**
     * 返回某个用户某门课的学习记录
     * @param courseId  课程编号
     * @param userId   用户编号
     * @return  学习记录
     */
    @GetMapping("list")
    @ResponseBody
    public MessageVO<List<StudyRecordInfoVO>> studyRecords(@RequestParam(value = "courseId", required = false) String courseId,
                                                           @RequestParam("userId") String userId) {
        List<StudyRecordInfoVO> list;
        if (courseId != null) {
            list = studyRecordService.getStudyRecordInfo(userId, courseId);
        } else {
            list = studyRecordService.getStudyRecordInfo(userId);
        }
        MessageVO<List<StudyRecordInfoVO>> messageVO = new MessageVO<>();
        if (list.size() == 0) {
            messageVO.setCode(-1);
            messageVO.setMsg("当前用户没有学习记录！");
        } else {
            messageVO.setCode(0);
            messageVO.setMsg("success");
            messageVO.setData(list);
        }
        return messageVO;
    }

    /**
     *
     * @param uId   用户编号
     * @param sectionId  章节编号
     * @param courseId  课程编号
     * @return
     */
    @PostMapping("/")
    @ResponseBody
    public MessageVO<String> addStudyRecord(@RequestParam("uId") Integer uId,
                                            @RequestParam("sectionId") Integer sectionId,
                                            @RequestParam("courseId") Integer courseId) {
        MessageVO<String> messageVO = new MessageVO<>();
        StudyRecord studyRecord = new StudyRecord();
        studyRecord.setCourseId(courseId);
        studyRecord.setSectionId(sectionId);
        studyRecord.setUId(uId);
        log.info(studyRecord.toString());
        try {
            studyRecordService.save(studyRecord);
            messageVO.setCode(0);
            messageVO.setMsg("success");
        } catch (Exception e) {
            messageVO.setCode(-1);
            messageVO.setMsg("保存学习记录失败！");
            messageVO.setData(e.getMessage());
            log.error(e.getMessage());
        }
        return messageVO;
    }

}
