package edu.hogwarts.course.controller;

import edu.hogwarts.course.model.Course;
import edu.hogwarts.course.repository.CourseRepository;
import edu.hogwarts.student.model.Student;
import edu.hogwarts.student.repository.StudentRepository;
import edu.hogwarts.teacher.model.Teacher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    public CourseController(CourseRepository courseRepository, StudentRepository studentRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    @GetMapping
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourse(@PathVariable int id) {
        Optional<Course> course = courseRepository.findById(id);
        return ResponseEntity.of(course);
    }

    @GetMapping("/{id}/teachers")
    public ResponseEntity<Teacher> getCourseTeacher(@PathVariable int id) {
        Optional<Course> course = courseRepository.findById(id);
        if (course.isPresent()) {
            Teacher teacher = course.get().getTeacher();
            return ResponseEntity.ok().body(teacher);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/students")
    public ResponseEntity<Set<Student>> getCourseStudents(@PathVariable int id) {
        Optional<Course> course = courseRepository.findById(id);
        if (course.isPresent()) {
            Set<Student> students = course.get().getStudents();
            return ResponseEntity.ok().body(students);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Course createCourse(@RequestBody Course course) {
        return courseRepository.save(course);
    }

    @PostMapping("/{id}/students")
    public ResponseEntity<Course> addStudentsToCourse(@PathVariable int id, @RequestBody List<Object> studentIdentifiers) {
        Optional<Course> courseOptional = courseRepository.findById(id);
        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();
            Set<Student> students = new HashSet<>();

            for (Object identifier : studentIdentifiers) {
                if (identifier instanceof Integer) {
                    int studentId = (Integer) identifier;
                    Optional<Student> studentOptional = studentRepository.findById(studentId);
                    studentOptional.ifPresent(students::add);
                    if (studentOptional.isEmpty()) {
                        return ResponseEntity.notFound().build();
                    }
                } else if (identifier instanceof String name) {
                    List<Student> matchingStudents = studentRepository.findByFullNameContainingIgnoreCase(name);
                    if (!matchingStudents.isEmpty()) {
                        students.addAll(matchingStudents);
                    } else {
                        return ResponseEntity.notFound().build();
                    }
                } else {
                    return ResponseEntity.badRequest().build();
                }
            }
            course.getStudents().addAll(students);
            Course updatedCourse = courseRepository.save(course);
            return ResponseEntity.ok(updatedCourse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{id}")
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
    @DeleteMapping("/{id}")
    public ResponseEntity<Course> deleteCourse(@PathVariable int id) {
        Optional<Course> course = courseRepository.findById(id);
        course.ifPresent(courseRepository::delete);
        return ResponseEntity.of(course);
    }
}
