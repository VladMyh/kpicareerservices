package com.kpics.service.impl;

import com.kpics.domain.StudentInfo;
import com.kpics.repository.StudentInfoRepository;
import com.kpics.service.StudentInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing StudentInfo.
 */
@Service
public class StudentInfoServiceImpl implements StudentInfoService{

    private final Logger log = LoggerFactory.getLogger(StudentInfoServiceImpl.class);

    private final StudentInfoRepository studentInfoRepository;

    public StudentInfoServiceImpl(StudentInfoRepository studentInfoRepository) {
        this.studentInfoRepository = studentInfoRepository;
    }

    @Override
    public StudentInfo save(StudentInfo studentInfo) {
        log.debug("Saving studentInfo" , studentInfo);
        return studentInfoRepository.save(studentInfo);
    }

    @Override
    public StudentInfo createStudentInfo(String faculty, String department,
                                         String group, String github, String linkedin,
                                         String about, String userId) {
        log.debug("Creating student info", faculty, department, group, github, linkedin, about, userId);

        StudentInfo studentInfo = new StudentInfo();
        studentInfo.faculty(faculty)
            .department(department)
            .group(group)
            .github(github)
            .linkedin(linkedin)
            .about(about)
            .user(userId);

        log.debug("Created Information for StudentInfo: {}", studentInfo);
        return studentInfoRepository.save(studentInfo);
    }

    @Override
    public Page<StudentInfo> findAll(Pageable pageable) {
        log.debug("Request to get all StudentInfos");
        Page<StudentInfo> result = studentInfoRepository.findAll(pageable);
        return result;
    }

    @Override
    public StudentInfo findOne(String id) {
        log.debug("Request to get StudentInfo : {}", id);
        StudentInfo studentInfo = studentInfoRepository.findOne(id);
        return studentInfo;
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete StudentInfo : {}", id);
        studentInfoRepository.delete(id);
    }

    @Override
    public Optional<StudentInfo> findOneByUserId(String id) {
        log.debug("Request to find StudentInfo by userId", id);
        return  studentInfoRepository.findOneByUserId(id);
    }
}
