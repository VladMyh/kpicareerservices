package com.kpics.service.impl;

import com.kpics.domain.TeacherInfo;
import com.kpics.repository.TeacherInfoRepository;
import com.kpics.service.TeacherInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing TeacherInfo.
 */
@Service
public class TeacherInfoServiceImpl implements TeacherInfoService{

    private final Logger log = LoggerFactory.getLogger(TeacherInfoServiceImpl.class);

    private final TeacherInfoRepository teacherInfoRepository;

    public TeacherInfoServiceImpl(TeacherInfoRepository teacherInfoRepository) {
        this.teacherInfoRepository = teacherInfoRepository;
    }

    @Override
    public TeacherInfo save(TeacherInfo teacherInfo) {
        log.debug("Request to save TeacherInfo : {}", teacherInfo);
        TeacherInfo result = teacherInfoRepository.save(teacherInfo);
        return result;
    }

    @Override
    public Page<TeacherInfo> findAll(Pageable pageable) {
        log.debug("Request to get all TeacherInfos");
        Page<TeacherInfo> result = teacherInfoRepository.findAll(pageable);
        return result;
    }

    @Override
    public TeacherInfo findOne(String id) {
        log.debug("Request to get TeacherInfo : {}", id);
        TeacherInfo teacherInfo = teacherInfoRepository.findOne(id);
        return teacherInfo;
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete TeacherInfo : {}", id);
        teacherInfoRepository.delete(id);
    }

    @Override
    public TeacherInfo createTeacherInfo(String faculty, String department, String about, String userId) {
        log.debug("Creating teacher info", faculty, department, about, userId);

        TeacherInfo teacherInfo = new TeacherInfo();
        teacherInfo.faculty(faculty)
            .department(department)
            .about(about)
            .user(userId);

        log.debug("Created Information for TeacherInfo: {}", teacherInfo);
        return teacherInfoRepository.save(teacherInfo);
    }

    @Override
    public Optional<TeacherInfo> findByUserId(String userId) {
        log.debug("Finding teacherInfo by id", userId);
        return teacherInfoRepository.findOneByUserId(userId);
    }
}
