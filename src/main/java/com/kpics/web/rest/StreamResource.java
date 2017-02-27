package com.kpics.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.kpics.domain.Stream;
import com.kpics.domain.Subject;
import com.kpics.domain.Track;
import com.kpics.service.StreamService;
import com.kpics.service.UserService;
import com.kpics.service.dto.UserDTO;
import com.kpics.web.rest.util.HeaderUtil;
import com.kpics.web.rest.util.PaginationUtil;
import com.kpics.web.rest.vm.StreamVM;
import com.kpics.web.rest.vm.TeacherVM;
import com.kpics.web.rest.vm.TrackVM;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Stream.
 */
@RestController
@RequestMapping("/api")
public class StreamResource {

    private final Logger log = LoggerFactory.getLogger(StreamResource.class);

    private static final String ENTITY_NAME = "stream";

    private static final String TRACK = "track";

    private static final String SUBJECT = "subject";

    private final StreamService streamService;

    private final UserService userService;

    public StreamResource(StreamService streamService, UserService userService) {
        this.streamService = streamService;
        this.userService = userService;
    }

    /**
     * POST  /streams : Create a new stream.
     *
     * @param streamVM the stream to create
     * @return the ResponseEntity with status 201 (Created) and with body the new stream, or with status 400 (Bad Request) if the stream has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/streams")
    @Timed
    public ResponseEntity<Stream> createStream(@Valid @RequestBody StreamVM streamVM) throws URISyntaxException {
        log.debug("REST request to save Stream : {}", streamVM);
        if (streamVM.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new stream cannot already have an ID")).body(null);
        }

        Stream stream = new Stream().name(streamVM.getName())
                                    .startDate(streamVM.getStartDate())
                                    .endDate(streamVM.getEndDate())
                                    .description(streamVM.getDescription());

        stream = streamService.save(stream);
        return ResponseEntity.created(new URI("/api/streams/" + stream.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, stream.getId()))
            .body(stream);
    }

    /**
     * PUT  /streams : Updates an existing stream.
     *
     * @param streamVM the stream to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated stream,
     * or with status 400 (Bad Request) if the stream is not valid,
     * or with status 500 (Internal Server Error) if the stream couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/streams")
    @Timed
    public ResponseEntity<Stream> updateStream(@Valid @RequestBody StreamVM streamVM) throws URISyntaxException {
        log.debug("REST request to update Stream : {}", streamVM);
        if (streamVM.getId() == null) {
            return createStream(streamVM);
        }

        Stream stream = new Stream().name(streamVM.getName())
                                    .startDate(streamVM.getStartDate())
                                    .endDate(streamVM.getEndDate())
                                    .description(streamVM.getDescription())
                                    .id(streamVM.getId());

        HashSet<Track> tracks = new HashSet<>(streamVM.getTracks()
            .stream()
            .map(t -> new Track(t.getId(),
                                t.getName(),
                                t.getDescription(),
                                t.getTeachers().stream()
                                               .map(TeacherVM::getId)
                                               .collect(Collectors.toSet()),
                                t.getSubjects()))
            .collect(Collectors.toSet()));

        stream.setTracks(tracks);

        stream = streamService.save(stream);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, stream.getId()))
            .body(stream);
    }

    /**
     * GET  /streams : get all the streams.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of streams in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/streams")
    @Timed
    public ResponseEntity<List<StreamVM>> getAllStreams(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Streams");
        Page<Stream> page = streamService.findAll(pageable);

        List<StreamVM> result = page.getContent()
            .stream()
            .map(l -> new StreamVM(l.getId(), l.getName(), l.getStartDate(), l.getEndDate(), l.getDescription(), l.getTracks()
                .stream()
                .map(t -> new TrackVM(t.getId(), t.getName(), t.getDescription(), t.getTeacherIds()
                    .stream()
                    .map(id -> new TeacherVM(new UserDTO(userService.getUserWithAuthorities(id))))
                    .collect(Collectors.toSet()), t.getSubjects()))
                .collect(Collectors.toSet())))
            .collect(Collectors.toList());

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/streams");
        return new ResponseEntity<>(result, headers, HttpStatus.OK);
    }

    /**
     * GET  /streams/:id : get the "id" stream.
     *
     * @param id the id of the stream to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the stream, or with status 404 (Not Found)
     */
    @GetMapping("/streams/{id}")
    @Timed
    public ResponseEntity<StreamVM> getStream(@PathVariable String id) {
        log.debug("REST request to get Stream : {}", id);
        Stream stream = streamService.findOne(id);
        StreamVM result = null;

        if(stream != null) {
            result = new StreamVM();
            result.setId(stream.getId());
            result.setName(stream.getName());
            result.setDescription(stream.getDescription());
            result.setStartDate(stream.getStartDate());
            result.setEndDate(stream.getEndDate());
            result.setTracks(stream.getTracks()
                .stream()
                .map(t -> new TrackVM(t.getId(), t.getName(), t.getDescription(), t.getTeacherIds()
                    .stream()
                    .map(i -> new TeacherVM(new UserDTO(userService.getUserWithAuthorities(i))))
                    .collect(Collectors.toSet()), t.getSubjects()))
                .collect(Collectors.toSet()));
        }

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    /**
     * DELETE  /streams/:id : delete the "id" stream.
     *
     * @param id the id of the stream to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/streams/{id}")
    @Timed
    public ResponseEntity<Void> deleteStream(@PathVariable String id) {
        log.debug("REST request to delete Stream : {}", id);
        streamService.delete(id);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id))
            .build();
    }

    /**
     * PUT  /streams : Add track to existing stream.
     *
     * @param trackVM the track to add to the stream
     * @return the ResponseEntity with status 200 (OK) and with body the updated stream,
     * or with status 400 (Bad Request) if the stream is not valid,
     * or with status 500 (Internal Server Error) if the stream couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/streams/{id}/tracks")
    @Timed
    public ResponseEntity<Stream> addTrackToStream(@PathVariable String id, @Valid @RequestBody TrackVM trackVM) throws URISyntaxException {
        log.debug("REST request to add Track to Stream : {}", id, trackVM);
        if (trackVM.getId() != null) {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new track cannot be created already have an ID"))
                .body(null);
        }

        Stream stream = streamService.findOne(id);

        if(stream != null) {
            trackVM.setId(ObjectId.get().toString());

            Track track = new Track();
            track.setId(trackVM.getId());
            track.setName(trackVM.getName());
            track.setDescription(trackVM.getDescription());

            stream.getTracks().add(track);
            streamService.save(stream);

            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, stream.getId()))
                .body(stream);
        }

        return ResponseEntity.badRequest()
            .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "iddoesntexist", "Stream with giver id doesn't exists"))
            .body(null);
    }

    /**
     * GET  /streams/:streamId/tracks/:trackId : get the track of the stream.
     *
     * @param streamId the id of the stream
     * @param trackId  the id of the track to retrieve
     * @return         the ResponseEntity with status 200 (OK) and with body the stream, or with status 404 (Not Found)
     */
    @GetMapping("/streams/{streamId}/tracks/{trackId}")
    @Timed
    public ResponseEntity<TrackVM> getTrack(@PathVariable String streamId, @PathVariable String trackId) {
        log.debug("REST request to get track from stream : {}", streamId, trackId);
        Stream stream = streamService.findOne(streamId);
        TrackVM result = null;

        if(stream != null) {
            Optional<Track> track = stream.getTracks()
                .stream()
                .filter(t -> t.getId().equals(trackId))
                .findFirst();

            if(track.isPresent()) {
                result = new TrackVM();
                result.setId(track.get().getId());
                result.setName(track.get().getName());
                result.setDescription(track.get().getDescription());
                result.setTeachers(track.get().getTeacherIds()
                    .stream()
                    .map(id -> new TeacherVM(new UserDTO(userService.getUserWithAuthorities(id))))
                    .collect(Collectors.toSet()));
            }
        }

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }

    /**
     * DELETE  /streams/:streamId/tracks/:trackId : delete the track of the stream.
     *
     * @param streamId the id of the stream
     * @param trackId  the id of the track to delete
     * @return         the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/streams/{streamId}/tracks/{trackId}")
    @Timed
    public ResponseEntity<?> deleteTrack(@PathVariable String streamId, @PathVariable String trackId) {
        log.debug("REST request to delete track from stream : {}", streamId, trackId);

        if(streamService.deleteTrack(streamId, trackId)) {
            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityDeletionAlert(TRACK, trackId))
                .build();
        }
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityDeletionFailedAlert(TRACK, trackId))
            .build();
    }

    /**
     * DELETE  /streams/:streamId/tracks/:trackId/subjects/:subjectId : delete the track of the stream.
     *
     * @param streamId  the id of the stream
     * @param trackId   the id of the track
     * @param subjectId the id of the subject to delete
     * @return         the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/streams/{streamId}/tracks/{trackId}/subjects/{subjectId}")
    @Timed
    public ResponseEntity<?> deleteSubject(@PathVariable String streamId,
                                           @PathVariable String trackId,
                                           @PathVariable String subjectId) {
        log.debug("REST request to delete subject from track : {}", streamId, trackId, subjectId);

        if(streamService.deleteSubject(streamId, trackId, subjectId)) {
            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityDeletionAlert(SUBJECT, subjectId))
                .build();
        }
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityDeletionFailedAlert(SUBJECT, subjectId))
            .build();
    }

    /**
     * PUT  /streams : Update track, add teacher to track.
     *
     * @param streamId   the id of the stream
     * @param trackId    the id of the track
     * @param teacherId    track entity.
     * @return           the ResponseEntity with status 200 (OK) and with body the updated stream,
     * or with status 400 (Bad Request) if the stream is not valid,
     * or with status 500 (Internal Server Error) if the stream couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/streams/{streamId}/tracks/{trackId}/addTeacher")
    @Timed
    public ResponseEntity<Stream> addTeacher(@PathVariable String streamId,
                                              @PathVariable String trackId,
                                              @RequestBody String teacherId) throws URISyntaxException {
        log.debug("REST request to add teacher to the Track: {}", streamId, trackId, teacherId);
        Stream stream = streamService.findOne(streamId);

        if(stream != null) {
            HashSet<Track> tracks = (HashSet<Track>) stream.getTracks();
            for(Track t : tracks) {
                if(t.getId().equals(trackId)) {
                    t.getTeacherIds().add(teacherId);
                }
            }

            streamService.save(stream);

            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, stream.getId()))
                .body(stream);
        }

        return ResponseEntity.badRequest()
            .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "iddoesntexist", "Stream with giver id doesn't exists"))
            .body(null);
    }

    /**
     * PUT  /streams : Update track, add subject to track.
     *
     * @param streamId   the id of the stream
     * @param trackId    the id of the track
     * @param subject    subject entity.
     * @return           the ResponseEntity with status 200 (OK) and with body the updated stream,
     * or with status 400 (Bad Request) if the stream is not valid,
     * or with status 500 (Internal Server Error) if the stream couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/streams/{streamId}/tracks/{trackId}/addSubject")
    @Timed
    public ResponseEntity<Stream> addSubject(@PathVariable String streamId,
                                             @PathVariable String trackId,
                                             @RequestBody Subject subject) throws URISyntaxException {
        log.debug("REST request to add subject to the Track: {}", streamId, trackId, subject);
        Stream stream = streamService.findOne(streamId);

        if(stream != null) {
            subject.setId(ObjectId.get().toString());

            HashSet<Track> tracks = (HashSet<Track>) stream.getTracks();
            for(Track t : tracks) {
                if(t.getId().equals(trackId)) {
                    t.getSubjects().add(subject);
                }
            }

            streamService.save(stream);

            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, stream.getId()))
                .body(stream);
        }

        return ResponseEntity.badRequest()
            .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "iddoesntexist", "Stream with giver id doesn't exists"))
            .body(null);
    }

}
