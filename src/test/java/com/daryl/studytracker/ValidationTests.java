package com.daryl.studytracker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.daryl.studytracker.assignment.Assignment;
import com.daryl.studytracker.course.Course;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

class ValidationTests {

    private final Validator validator =
            Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void courseWithBlankNameIsRejected() {
        Course course = new Course("", "Dr. Smith", "Fall 2026");

        Set<ConstraintViolation<Course>> violations = validator.validate(course);

        assertEquals(1, violations.size());
    }

    @Test
    void validCourseIsAccepted() {
        Course course = new Course("Data Structures", "Dr. Smith", "Fall 2026");

        assertTrue(validator.validate(course).isEmpty());
    }

    @Test
    void assignmentWithoutTitleOrDueDateIsRejected() {
        Assignment assignment = new Assignment("", null, null);

        Set<ConstraintViolation<Assignment>> violations = validator.validate(assignment);

        assertEquals(2, violations.size());
    }

    @Test
    void validAssignmentIsAccepted() {
        Assignment assignment = new Assignment("AVL Tree lab", LocalDate.of(2026, 10, 30), null);

        assertTrue(validator.validate(assignment).isEmpty());
    }
}