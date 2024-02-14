package edu.hogwarts.course.model;

import edu.hogwarts.student.model.Student;
import edu.hogwarts.teacher.model.Teacher;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
private String subject;
private int schoolYear;
private boolean current;
private Teacher teacher;
private Student[] students;
}
