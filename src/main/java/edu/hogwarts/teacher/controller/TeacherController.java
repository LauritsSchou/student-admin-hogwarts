package edu.hogwarts.teacher.controller;

import edu.hogwarts.teacher.model.Teacher;
import edu.hogwarts.teacher.repository.TeacherRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class TeacherController {
    private final TeacherRepository teacherRepository;
    public TeacherController(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;}

@GetMapping("/teachers")
public List<Teacher> getAllTeachers(){
    return teacherRepository.findAll();
}
@GetMapping("/teachers/{id}")
public ResponseEntity<Teacher> getTeacher(@PathVariable int id){
Optional<Teacher> teacher = teacherRepository.findById(id);
return ResponseEntity.of(teacher);
}
@PostMapping("/teachers")
    @ResponseStatus(HttpStatus.CREATED)
    public Teacher createTeacher(@RequestBody Teacher teacher){
        return teacherRepository.save(teacher);}
@PutMapping("/teachers/{id}")
public ResponseEntity<Teacher> updateTeacher(@PathVariable int id, @RequestBody Teacher teacher){
Optional<Teacher> original = teacherRepository.findById(id);
if(original.isPresent()){
Teacher originalTeacher =original.get();
originalTeacher.setFirstName(teacher.getFirstName());
originalTeacher.setMiddleName(teacher.getMiddleName());
originalTeacher.setLastName(teacher.getLastName());
originalTeacher.setDateOfBirth(teacher.getDateOfBirth());
originalTeacher.setHouse(teacher.getHouse());
originalTeacher.setHeadOfHouse(teacher.isHeadOfHouse());
originalTeacher.setEmployment(teacher.getEmployment());
originalTeacher.setEmploymentStart(teacher.getEmploymentStart());
originalTeacher.setEmploymentEnd(teacher.getEmploymentEnd());
Teacher updatedTeacher =teacherRepository.save(originalTeacher);
return ResponseEntity.ok().body(updatedTeacher);
}
else{
return ResponseEntity.notFound().build();
}
}
@DeleteMapping("/teachers/{id}")
    public ResponseEntity<Teacher> deleteTeacher(@PathVariable int id){
Optional<Teacher> teacher = teacherRepository.findById(id);
teacherRepository.deleteById(id);
return ResponseEntity.of(teacher);
}
}
