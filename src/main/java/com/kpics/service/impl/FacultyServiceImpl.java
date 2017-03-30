package com.kpics.service.impl;

import com.kpics.domain.Department;
import com.kpics.domain.Faculty;
import com.kpics.domain.Group;
import com.kpics.repository.FacultyRepository;
import com.kpics.service.FacultyService;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Faculty.
 */
@Service
public class FacultyServiceImpl implements FacultyService{

    private final Logger log = LoggerFactory.getLogger(FacultyServiceImpl.class);

    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty save(Faculty faculty) {
        log.debug("Request to save Faculty : {}", faculty);

        return facultyRepository.save(faculty);
    }

    @Override
    public Page<Faculty> findAll(Pageable pageable) {
        log.debug("Request to get all Faculties");

        return facultyRepository.findAll(pageable);
    }

    @Override
    public Faculty findOne(String id) {
        log.debug("Request to get Faculty : {}", id);

        return facultyRepository.findOne(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Faculty : {}", id);

        facultyRepository.delete(id);
    }

    @Override
    public Faculty saveDepartment(String facultyId, Department department) {
        log.debug("Request to save department, facultyId: {}, department: {}", facultyId, department);

        Faculty faculty = findOne(facultyId);

        if(faculty != null) {
            if(department.getId() == null) {
                if(faculty.getDepartments() == null) {
                    HashSet<Department> departments = new HashSet<>();
                    department.setId(ObjectId.get().toString());
                    departments.add(department);
                    faculty.setDepartments(departments);

                    save(faculty);
                }
                else {
                    Optional<Department> dep = faculty.getDepartments()
                        .stream()
                        .filter(d -> d.getName().equals(department.getName()))
                        .findFirst();

                    if(!dep.isPresent()) {
                        department.setId(ObjectId.get().toString());
                        faculty.getDepartments().add(department);

                        save(faculty);
                    }
                }
            }
            else {
                faculty.setDepartments(faculty.getDepartments()
                    .stream()
                    .map(d -> d.getId().equals(department.getId()) ? department : d)
                    .collect(Collectors.toSet()));

                save(faculty);
            }
        }

        return faculty;
    }

    @Override
    public void deleteDepartment(String facultyId, String departmentId) {
        log.debug("Request to delete department, facultyId: {}, departmentId: {}");

        Faculty faculty = findOne(facultyId);

        if(faculty != null) {
            Optional<Department> department = faculty.getDepartments()
                .stream()
                .filter(d -> d.getId().equals(departmentId))
                .findFirst();

            if(department.isPresent()) {
                faculty.setDepartments(faculty.getDepartments()
                    .stream()
                    .filter(d -> !d.getId().equals(departmentId))
                    .collect(Collectors.toSet()));

                save(faculty);
            }
        }
    }

    @Override
    public Department findDepartment(String facultyId, String departmentId) {
        log.debug("Request to find department, facultyId: {}, departmentId: {}", facultyId, departmentId);

        Department result = null;
        Faculty faculty = findOne(facultyId);

        if(faculty != null) {
            Optional<Department> department = faculty.getDepartments()
                .stream()
                .filter(d -> d.getId().equals(departmentId))
                .findFirst();

            if(department.isPresent()) {
                result = department.get();
            }
        }

        return result;
    }
}
