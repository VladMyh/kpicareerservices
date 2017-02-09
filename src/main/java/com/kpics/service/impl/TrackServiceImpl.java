package com.kpics.service.impl;

import com.kpics.domain.Track;
import com.kpics.repository.TrackRepository;
import com.kpics.service.TrackService;
import com.kpics.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing Track.
 */
@Service
public class TrackServiceImpl implements TrackService{

    private final Logger log = LoggerFactory.getLogger(TrackServiceImpl.class);

    private final TrackRepository trackRepository;

    private final UserService userService;

    public TrackServiceImpl(TrackRepository trackRepository, UserService userService) {
        this.trackRepository = trackRepository;
        this.userService = userService;
    }

    @Override
    public Track save(Track track) {
        log.debug("Request to save Track : {}", track);
        Track result = trackRepository.save(track);
        return result;
    }

    @Override
    public Page<Track> findAll(Pageable pageable) {
        log.debug("Request to get all Tracks");
        Page<Track> result = trackRepository.findAll(pageable);
        return result;
    }

    @Override
    public Track findOne(String id) {
        log.debug("Request to get Track : {}", id);
        Track track = trackRepository.findOne(id);
        return track;
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Track : {}", id);
        trackRepository.delete(id);
    }

    @Override
    public Optional<Track> findByName(String name) {
        log.debug("Request to find track by name", name);
        return trackRepository.findOneByName(name);
    }
}
