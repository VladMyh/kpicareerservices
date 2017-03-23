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

    /**
     * Save a group.
     *
     * @param group the entity to save
     * @return the persisted entity
     */
    @Override
    public Group save(Group group) {
        log.debug("Request to save Group : {}", group);
        Group result = groupRepository.save(group);
        return result;
    }

    /**
     *  Get all the groups.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    public Page<Group> findAll(Pageable pageable) {
        log.debug("Request to get all Groups");
        Page<Group> result = groupRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one group by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public Group findOne(String id) {
        log.debug("Request to get Group : {}", id);
        Group group = groupRepository.findOne(id);
        return group;
    }

    /**
     *  Delete the  group by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Group : {}", id);
        groupRepository.delete(id);
    }
}
