package edu.hogwarts.course.controller;

import edu.hogwarts.course.model.Course;
import edu.hogwarts.course.repository.CourseRepository;
import edu.hogwarts.student.model.Student;
import edu.hogwarts.teacher.model.Teacher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CourseController {
    private final CourseRepository courseRepository;

    public CourseController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @GetMapping("/courses")
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @GetMapping("/courses/{id}")
    public ResponseEntity<Course> getCourse(@PathVariable int id) {
        Optional<Course> course = courseRepository.findById(id);
        return ResponseEntity.of(course);
    }

    @GetMapping("/courses/{id}/teachers")
    public ResponseEntity<List<Teacher>> getCourseTeachers(@PathVariable int id) {
        Optional<Course> course = courseRepository.findById(id);
        if (course.isPresent()) {
            List<Teacher> teachers = (List<Teacher>) course.get().getTeacher();
            return ResponseEntity.ok().body(teachers);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/courses/{id}/students")
    public ResponseEntity<List<Student>> getCourseStudents(@PathVariable int id) {
        Optional<Course> course = courseRepository.findById(id);
        if (course.isPresent()) {
            List<Student> students = (List<Student>) course.get().getStudents();
            return ResponseEntity.ok().body(students);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/courses")
    @ResponseStatus(HttpStatus.CREATED)
    public Course createCourse(@RequestBody Course course) {
        return courseRepository.save(course);
    }

    @PutMapping("/courses/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable int id, @RequestBody Course course) {
        Optional<Course> original = courseRepository.findById(id);
        if (original.isPresent()) {
            Course originalCourse = original.get();
            originalCourse.setSubject(course.getSubject());
            originalCourse.setSchoolYear(course.getSchoolYear());
            originalCourse.setCurrent(course.isCurrent());
            originalCourse.setTeacher(course.getTeacher());
            originalCourse.setStudents(course.getStudents());
            Course updatedCourse = courseRepository.save(originalCourse);
            return ResponseEntity.ok().body(updatedCourse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/courses/{id}")
    public ResponseEntity<Course> deleteCourse(@PathVariable int id) {
        Optional<Course> course = courseRepository.findById(id);
        course.ifPresent(courseRepository::delete);
        return ResponseEntity.of(course);
    }
}
