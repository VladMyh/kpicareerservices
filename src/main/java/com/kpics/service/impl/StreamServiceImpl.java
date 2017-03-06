package com.kpics.service.impl;

import com.kpics.domain.Stream;
import com.kpics.domain.Subject;
import com.kpics.domain.Track;
import com.kpics.repository.StreamRepository;
import com.kpics.service.StreamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Override
    public boolean deleteTrack(String streamId, String trackId) {
        log.debug("Request to delete Track : {}", streamId, trackId);

        Stream stream = streamRepository.findOne(streamId);

        if(stream != null) {
            Optional<Track> track = stream.getTracks()
                .stream()
                .filter(t -> t.getId().equals(trackId))
                .findFirst();

            if(track.isPresent() && track.get().getSubjects().isEmpty()) {
                Set<Track> newTracks = stream.getTracks()
                    .stream()
                    .filter(t -> !t.getId().equals(trackId))
                    .collect(Collectors.toSet());

                stream.setTracks(newTracks);
                streamRepository.save(stream);

                return true;
            }
        }

        return false;
    }

    @Override
    public boolean deleteSubject(String streamId, String trackId, String subjectId) {
        log.debug("Request to delete Subject : {}", streamId, trackId, subjectId);

        Stream stream = streamRepository.findOne(streamId);

        if(stream != null) {
            Optional<Track> track = stream.getTracks()
                .stream()
                .filter(t -> t.getId().equals(trackId))
                .findFirst();

            if(track.isPresent() && !track.get().getSubjects().isEmpty()) {
                Set<Subject> newSubjects = track.get().getSubjects()
                    .stream()
                    .filter(s -> !s.getId().equals(subjectId))
                    .collect(Collectors.toSet());

                track.get().setSubjects(newSubjects);

                stream.setTracks(stream.getTracks()
                    .stream()
                    .map(t -> t.getId().equals(trackId) ? track.get() : t)
                    .collect(Collectors.toSet()));

                streamRepository.save(stream);

                return true;
            }
        }

        return false;
    }

    @Override
    public boolean deleteTeacher(String streamId, String trackId, String teacherId) {
        log.debug("Request to delete teacher from track, streamId : {}, trackId: {}, teacherId: {}",
            streamId, trackId, teacherId);

        Stream stream = streamRepository.findOne(streamId);

        if(stream != null) {
            Optional<Track> track = stream.getTracks()
                .stream()
                .filter(t -> t.getId().equals(trackId))
                .findFirst();

            if(track.isPresent() && !track.get().getTeacherIds().isEmpty()) {
                Optional<Subject> subject = track.get().getSubjects()
                    .stream()
                    .filter(s -> s.getTeacherId().equals(teacherId))
                    .findFirst();

                if(!subject.isPresent()) {
                    Set<String> newTeachers = track.get().getTeacherIds()
                        .stream()
                        .filter(t -> !t.equals(teacherId))
                        .collect(Collectors.toSet());

                    track.get().setTeacherIds(newTeachers);

                    stream.setTracks(stream.getTracks()
                        .stream()
                        .map(t -> t.getId().equals(trackId) ? track.get() : t)
                        .collect(Collectors.toSet()));

                    streamRepository.save(stream);

                    return true;
                }
            }
        }

        return false;
    }
}
