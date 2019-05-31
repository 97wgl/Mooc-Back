package com.xgxfd.moocback.service.impl;

import com.xgxfd.moocback.entity.StudyRecord;
import com.xgxfd.moocback.mapper.StudyRecordMapper;
import com.xgxfd.moocback.service.StudyRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xgxfd.moocback.vo.StudyRecordInfoVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Xxz Wgl
 * @since 2019-05-27
 */
@Service
public class StudyRecordServiceImpl extends ServiceImpl<StudyRecordMapper, StudyRecord> implements StudyRecordService {

    @Override
    public List<StudyRecordInfoVO> getStudyRecordInfo(String userId) {
        return this.baseMapper.getStudyRecordInfo(userId);
    }

    @Override
    public List<StudyRecordInfoVO> getStudyRecordInfo(String userId, String courseId) {
        return this.baseMapper.getStudyRecordInfo(userId, courseId);
    }
}
