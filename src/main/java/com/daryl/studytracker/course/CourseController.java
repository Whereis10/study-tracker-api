package com.daryl.studytracker.course;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.daryl.studytracker.assignment.AssignmentRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseRepository courseRepository;
    private final AssignmentRepository assignmentRepository;

    public CourseController(CourseRepository courseRepository, AssignmentRepository assignmentRepository) {
        this.courseRepository = courseRepository;
        this.assignmentRepository = assignmentRepository;
        
    }

    @GetMapping
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Course createCourse(@Valid @RequestBody Course course) {
        return courseRepository.save(course);
    }
   
    @PutMapping("/{id}")
    public Course updateCourse(@PathVariable Long id,@Valid @RequestBody Course updated) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));
        course.setName(updated.getName());
        course.setInstructor(updated.getInstructor());
        course.setSemester(updated.getSemester());
        return courseRepository.save(course);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void deleteCourse(@PathVariable Long id) {
        if (!courseRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found");
        }
        assignmentRepository.deleteByCourseId(id);
        courseRepository.deleteById(id);
    }
    
}