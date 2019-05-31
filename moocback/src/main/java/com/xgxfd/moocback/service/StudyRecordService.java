package com.xgxfd.moocback.service;

import com.xgxfd.moocback.entity.StudyRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xgxfd.moocback.vo.StudyRecordInfoVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Xxz Wgl
 * @since 2019-05-27
 */
public interface StudyRecordService extends IService<StudyRecord> {
    List<StudyRecordInfoVO> getStudyRecordInfo(String userId);

    List<StudyRecordInfoVO> getStudyRecordInfo(String userId, String courseId);
}
