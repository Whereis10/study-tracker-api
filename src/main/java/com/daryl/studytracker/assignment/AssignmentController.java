package com.daryl.studytracker.assignment;

import java.util.List;

import org.springframework.http.HttpStatus;
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

import com.daryl.studytracker.course.Course;
import com.daryl.studytracker.course.CourseRepository;

@RestController
@RequestMapping("/courses/{courseId}/assignments")
public class AssignmentController {

    private final AssignmentRepository assignmentRepository;
    private final CourseRepository courseRepository;

    public AssignmentController(AssignmentRepository assignmentRepository,
                                CourseRepository courseRepository) {
        this.assignmentRepository = assignmentRepository;
        this.courseRepository = courseRepository;
    }

    @GetMapping
    public List<Assignment> getAssignments(@PathVariable Long courseId) {
        if (!courseRepository.existsById(courseId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found");
        }
        return assignmentRepository.findByCourseId(courseId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Assignment createAssignment(@PathVariable Long courseId,
                                       @RequestBody Assignment assignment) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));
        assignment.setCourse(course);
        return assignmentRepository.save(assignment);
    }
    
    @PutMapping("/{id}")
    public Assignment updateAssignment(@PathVariable Long courseId,
                                       @PathVariable Long id,
                                       @RequestBody Assignment updated) {
        Assignment assignment = assignmentRepository.findById(id)
                .filter(a -> a.getCourse().getId().equals(courseId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Assignment not found"));
        assignment.setTitle(updated.getTitle());
        assignment.setDueDate(updated.getDueDate());
        assignment.setCompleted(updated.isCompleted());
        return assignmentRepository.save(assignment);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAssignment(@PathVariable Long courseId, @PathVariable Long id) {
        Assignment assignment = assignmentRepository.findById(id)
                .filter(a -> a.getCourse().getId().equals(courseId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Assignment not found"));
        assignmentRepository.delete(assignment);
    }
}