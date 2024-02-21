package edu.hogwarts.teacher.controller;

import edu.hogwarts.teacher.dto.TeacherGetDTO;
import edu.hogwarts.teacher.dto.TeacherPatchDTO;
import edu.hogwarts.teacher.model.Teacher;
import edu.hogwarts.teacher.repository.TeacherRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/teachers")
public class TeacherController {
    private final TeacherRepository teacherRepository;
    public TeacherController(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;}

@GetMapping()
public List<TeacherGetDTO> getAllTeachers(){
    List<Teacher> teachers = teacherRepository.findAll();

    List<TeacherGetDTO> teacherGetDTOS = new ArrayList<>();
    for (Teacher teacher : teachers){
        TeacherGetDTO getDTO = new TeacherGetDTO();
        getDTO.setId(teacher.getId());
        getDTO.setFirstName(teacher.getFirstName());
        getDTO.setMiddleName(teacher.getMiddleName());
        getDTO.setLastName(teacher.getLastName());
        getDTO.setDateOfBirth(teacher.getDateOfBirth());
        getDTO.setHouse(teacher.getHouse().getName());
        getDTO.setHeadOfHouse(teacher.isHeadOfHouse());
        getDTO.setEmployment(teacher.getEmployment());
        getDTO.setEmploymentStart(teacher.getEmploymentStart());
        getDTO.setEmploymentEnd(teacher.getEmploymentEnd());
        teacherGetDTOS.add(getDTO);
    }
    return teacherGetDTOS;
}
@GetMapping("/{id}")
public ResponseEntity<TeacherGetDTO> getTeacher(@PathVariable int id){
Optional<Teacher> teacherOptional = teacherRepository.findById(id);
if(teacherOptional.isEmpty()){
    return ResponseEntity.notFound().build();
}
Teacher teacher = teacherOptional.get();
    TeacherGetDTO getDTO = new TeacherGetDTO();
    getDTO.setId(teacher.getId());
    getDTO.setFirstName(teacher.getFirstName());
    getDTO.setMiddleName(teacher.getMiddleName());
    getDTO.setLastName(teacher.getLastName());
    getDTO.setDateOfBirth(teacher.getDateOfBirth());
    getDTO.setHouse(teacher.getHouse().getName());
    getDTO.setHeadOfHouse(teacher.isHeadOfHouse());
    getDTO.setEmployment(teacher.getEmployment());
    getDTO.setEmploymentStart(teacher.getEmploymentStart());
    getDTO.setEmploymentEnd(teacher.getEmploymentEnd());
    return ResponseEntity.ok(getDTO);

}
@PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Teacher createTeacher(@RequestBody Teacher teacher){
        return teacherRepository.save(teacher);}
@PutMapping("/{id}")
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
@PatchMapping("/{id}")
public ResponseEntity<Teacher> patchTeacher(@PathVariable int id, @RequestBody TeacherPatchDTO patchDTO){
        Optional<Teacher> originalOptional = teacherRepository.findById(id);
        if(originalOptional.isPresent()){
            Teacher originalTeacher = originalOptional.get();

        if(patchDTO.isHeadOfHouse() != originalTeacher.isHeadOfHouse()) {
            originalTeacher.setHeadOfHouse(patchDTO.isHeadOfHouse());
        }
        if(patchDTO.getEmploymentEnd() != null){
            originalTeacher.setEmploymentEnd(patchDTO.getEmploymentEnd());
        }
        if(patchDTO.getEmployment() != null){
            originalTeacher.setEmployment(patchDTO.getEmployment());
        }
        Teacher updatedTeacher = teacherRepository.save(originalTeacher);
        return ResponseEntity.ok(updatedTeacher);
}else {
        return ResponseEntity.notFound().build();}
}
@DeleteMapping("/{id}")
    public ResponseEntity<Teacher> deleteTeacher(@PathVariable int id){
Optional<Teacher> teacher = teacherRepository.findById(id);
teacherRepository.deleteById(id);
return ResponseEntity.of(teacher);
}
}
