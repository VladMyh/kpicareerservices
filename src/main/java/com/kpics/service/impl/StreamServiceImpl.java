package com.kpics.service.impl;

import com.kpics.domain.Stream;
import com.kpics.repository.StreamRepository;
import com.kpics.service.StreamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class StreamServiceImpl implements StreamService{

    private final Logger log = LoggerFactory.getLogger(StreamServiceImpl.class);

    private final StreamRepository streamRepository;

    public StreamServiceImpl(StreamRepository streamRepository) {
        this.streamRepository = streamRepository;
    }

    @Override
    public Stream save(Stream stream) {
        log.debug("Request to save Stream : {}", stream);
        return streamRepository.save(stream);
    }

    @Override
    public Page<Stream> findAll(Pageable pageable) {
        log.debug("Request to get all Streams");
        return streamRepository.findAll(pageable);
    }

    @Override
    public Stream findOne(String id) {
        log.debug("Request to get Stream : {}", id);
        return streamRepository.findOne(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Stream : {}", id);
        streamRepository.delete(id);
    }
}
