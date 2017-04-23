package com.kpics.service.impl;

import com.kpics.service.GroupService;
import com.kpics.domain.Group;
import com.kpics.repository.GroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing Group.
 */
@Service
public class GroupServiceImpl implements GroupService{

    private final Logger log = LoggerFactory.getLogger(GroupServiceImpl.class);

    private final GroupRepository groupRepository;

    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public Group save(Group group) {
        log.debug("Request to save Group : {}", group);
        return groupRepository.save(group);
    }

    @Override
    public Page<Group> findAll(Pageable pageable) {
        log.debug("Request to get all Groups");
        return groupRepository.findAll(pageable);
    }

    @Override
    public Group findOne(String id) {
        log.debug("Request to get Group : {}", id);
        return groupRepository.findOne(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Group : {}", id);
        groupRepository.delete(id);
    }

    @Override
    public List<Group> findByFaculty(String faculty) {
        log.debug("Request to find groups by faculty, faculty: {}", faculty);

        return groupRepository.findByFaculty(faculty);
    }

    @Override
    public List<Group> findByFacultyAndDepartment(String faculty, String department) {
        log.debug("Request to find groups by faculty and department, faculty: {}, department: {}",
            faculty, department);

        return groupRepository.findByFacultyAndDepartment(faculty, department);
    }
}
