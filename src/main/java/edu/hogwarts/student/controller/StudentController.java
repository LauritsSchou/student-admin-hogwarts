package edu.hogwarts.student.controller;

import edu.hogwarts.house.model.House;
import edu.hogwarts.house.repository.HouseRepository;
import edu.hogwarts.student.dto.StudentGetDTO;
import edu.hogwarts.student.dto.StudentPatchDTO;
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
    public List<StudentGetDTO> getAllStudents() {
        List<Student> students = studentRepository.findAll();

        List<StudentGetDTO> studentGetDTOS = new ArrayList<>();
        for (Student student : students) {
            StudentGetDTO getDTO = new StudentGetDTO();
            getDTO.setId(student.getId());
            getDTO.setFirstName(student.getFirstName());
            getDTO.setMiddleName(student.getMiddleName());
            getDTO.setLastName(student.getLastName());
            getDTO.setDateOfBirth(student.getDateOfBirth());
            getDTO.setHouse(student.getHouse().getName());
            getDTO.setSchoolYear(student.getSchoolYear());
            getDTO.setEnrollmentYear(student.getEnrollmentYear());
            getDTO.setGraduationYear(student.getGraduationYear());
            getDTO.setGraduated(student.isGraduated());
            studentGetDTOS.add(getDTO);
        }

        return studentGetDTOS;
    }


    @GetMapping("/{id}")
    public ResponseEntity<StudentGetDTO> getStudent(@PathVariable int id) {
        Optional<Student> studentOptional = studentRepository.findById(id);

        if (studentOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Student student = studentOptional.get();

        StudentGetDTO getDTO = new StudentGetDTO();
        getDTO.setId(student.getId());
        getDTO.setFirstName(student.getFirstName());
        getDTO.setMiddleName(student.getMiddleName());
        getDTO.setLastName(student.getLastName());
        getDTO.setDateOfBirth(student.getDateOfBirth());
        getDTO.setHouse(student.getHouse().getName());
        getDTO.setSchoolYear(student.getSchoolYear());
        getDTO.setEnrollmentYear(student.getEnrollmentYear());
        getDTO.setGraduationYear(student.getGraduationYear());
        getDTO.setGraduated(student.isGraduated());

        return ResponseEntity.ok(getDTO);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Student> createStudent(@RequestBody StudentGetDTO getDTO) {
        String name = getDTO.getName();
        String[] names = name.split("\\s+");

        Student student = new Student();

        if (names.length == 1) {
            student.setFirstName(names[0]);
            student.setMiddleName("");
            student.setLastName("");
        } else if (names.length == 2) {
            student.setFirstName(names[0]);
            student.setMiddleName("");
            student.setLastName(names[1]);
        } else if (names.length >= 3) {
            student.setFirstName(names[0]);
            student.setMiddleName(names[1]);
            student.setLastName(names[names.length - 1]);
        } else {
            return ResponseEntity.badRequest().build();
        }

        String houseName = getDTO.getHouse();
        House house = houseRepository.findByName(houseName).orElse(null);
        if (house == null) {
            return ResponseEntity.badRequest().build();
        }
        student.setDateOfBirth(getDTO.getDateOfBirth());
        student.setHouse(house);
        student.setPrefect(getDTO.isPrefect());
        student.setSchoolYear(getDTO.getSchoolYear());
        student.setEnrollmentYear(getDTO.getEnrollmentYear());
        student.setGraduationYear(getDTO.getGraduationYear());
        student.setGraduated(getDTO.isGraduated());
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
            originalStudent.setSchoolYear(student.getSchoolYear());
            originalStudent.setGraduationYear(student.getGraduationYear());
            originalStudent.setGraduated(student.isGraduated());

            Student updatedStudent = studentRepository.save(originalStudent);
            return ResponseEntity.ok().body(updatedStudent);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Student> patchStudent(@PathVariable int id, @RequestBody StudentPatchDTO patchDTO) {
        Optional<Student> originalOptional = studentRepository.findById(id);
        if (originalOptional.isPresent()) {
            Student originalStudent = originalOptional.get();

            if (patchDTO.isPrefect() != originalStudent.isPrefect()) {
                originalStudent.setPrefect(patchDTO.isPrefect());
            }
            if (patchDTO.getSchoolYear() != null) {
                originalStudent.setSchoolYear(patchDTO.getSchoolYear());
            }
            if(patchDTO.isGraduated() != originalStudent.isGraduated()){
                originalStudent.setGraduated(patchDTO.isGraduated());
            }
            if (patchDTO.getGraduationYear() != null) {
                originalStudent.setGraduationYear(patchDTO.getGraduationYear());
            }

            Student updatedStudent = studentRepository.save(originalStudent);

            return ResponseEntity.ok(updatedStudent);
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
