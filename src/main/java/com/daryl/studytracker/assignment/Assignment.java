package com.daryl.studytracker.assignment;

import java.time.LocalDate;

import com.daryl.studytracker.course.Course;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "assignments")
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    @Column(nullable = false)
    private String title;

    @NotNull(message = "Due date is required")
    private LocalDate dueDate;
    
    private boolean completed;

    @ManyToOne(optional = false)
    @JoinColumn(name = "course_id")
    private Course course;

    protected Assignment() {}

    public Assignment(String title, LocalDate dueDate, Course course) {
        this.title = title;
        this.dueDate = dueDate;
        this.course = course;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }
}