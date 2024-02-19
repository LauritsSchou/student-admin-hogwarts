package edu.hogwarts.student.controller;

import edu.hogwarts.house.model.House;
import edu.hogwarts.house.repository.HouseRepository;
import edu.hogwarts.student.dto.StudentDTO;
import edu.hogwarts.student.model.Student;
import edu.hogwarts.student.repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentRepository studentRepository;
    private final  HouseRepository houseRepository;

    public StudentController(StudentRepository studentRepository, HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
        this.studentRepository = studentRepository;
    }

    @GetMapping()
    public List<StudentDTO> getAllStudents() {
        List<Student> students = studentRepository.findAll();

        List<StudentDTO> studentDTOs = new ArrayList<>();
        for (Student student : students) {
            StudentDTO studentDTO = new StudentDTO();
            studentDTO.setFirstName(student.getFirstName());
            studentDTO.setMiddleName(student.getMiddleName());
            studentDTO.setLastName(student.getLastName());
            studentDTO.setDateOfBirth(student.getDateOfBirth());
            studentDTO.setHouse(student.getHouse().getName());
            studentDTO.setEnrollmentYear(student.getEnrollmentYear());
            studentDTO.setGraduationYear(student.getGraduationYear());
            studentDTO.setGraduated(student.isGraduated());
            studentDTOs.add(studentDTO);
        }

        return studentDTOs;
    }


    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudent(@PathVariable int id) {
        Optional<Student> studentOptional = studentRepository.findById(id);

        if (studentOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Student student = studentOptional.get();

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setFirstName(student.getFirstName());
        studentDTO.setMiddleName(student.getMiddleName());
        studentDTO.setLastName(student.getLastName());
        studentDTO.setDateOfBirth(student.getDateOfBirth());
        studentDTO.setHouse(student.getHouse().getName());
        studentDTO.setEnrollmentYear(student.getEnrollmentYear());
        studentDTO.setGraduationYear(student.getGraduationYear());
        studentDTO.setGraduated(student.isGraduated());

        return ResponseEntity.ok(studentDTO);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Student> createStudent(@RequestBody StudentDTO studentDTO) {
        String houseName = studentDTO.getHouse();
        House house = houseRepository.findByName(houseName).orElse(null);
        if (house == null) {
            return ResponseEntity.badRequest().build();
        }
        Student student = new Student();
        student.setFirstName(studentDTO.getFirstName());
        student.setMiddleName(studentDTO.getMiddleName());
        student.setLastName(studentDTO.getLastName());
        student.setDateOfBirth(studentDTO.getDateOfBirth());
        student.setHouse(house);
        student.setPrefect(studentDTO.isPrefect());
        student.setEnrollmentYear(studentDTO.getEnrollmentYear());
        student.setGraduationYear(studentDTO.getGraduationYear());
        student.setGraduated(studentDTO.isGraduated());
        Student savedStudent = studentRepository.save(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedStudent);
    }

    @PutMapping("{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable int id, @RequestBody Student student) {
        Optional<Student> original = studentRepository.findById(id);
        if (original.isPresent()) {
            Student originalStudent = original.get();
            originalStudent.setFirstName(student.getFirstName());
            originalStudent.setMiddleName(student.getMiddleName());
            originalStudent.setLastName(student.getLastName());
            originalStudent.setDateOfBirth(student.getDateOfBirth());
            originalStudent.setHouse(student.getHouse());
            originalStudent.setPrefect(student.isPrefect());
            originalStudent.setEnrollmentYear(student.getEnrollmentYear());
            originalStudent.setGraduationYear(student.getGraduationYear());
            originalStudent.setGraduated(student.isGraduated());

            Student updatedStudent = studentRepository.save(originalStudent);
            return ResponseEntity.ok().body(updatedStudent);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable int id) {
        Optional<Student> student = studentRepository.findById(id);
        studentRepository.deleteById(id);
        return ResponseEntity.of(student);
    }
}
