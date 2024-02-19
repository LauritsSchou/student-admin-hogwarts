package edu.hogwarts.config;

import edu.hogwarts.course.model.Course;
import edu.hogwarts.course.repository.CourseRepository;
import edu.hogwarts.house.model.House;
import edu.hogwarts.house.repository.HouseRepository;
import edu.hogwarts.teacher.model.EmploymentType;
import edu.hogwarts.teacher.model.Teacher;
import edu.hogwarts.teacher.repository.TeacherRepository;
import edu.hogwarts.student.model.Student;
import edu.hogwarts.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Component
public class DataLoader implements ApplicationRunner {

    private final HouseRepository houseRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public DataLoader(HouseRepository houseRepository, TeacherRepository teacherRepository,
                      StudentRepository studentRepository, CourseRepository courseRepository) {
        this.houseRepository = houseRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public void run(ApplicationArguments args) {

        createHouses();

        Teacher teacher = createTeacher();

        Set<Student> students = createStudents();

        createCourse(teacher, students);
    }

    private void createHouses() {


            House gryffindor = new House();
            gryffindor.setName("Gryffindor");
            gryffindor.setFounder("Godric Gryffindor");
            gryffindor.setColor1("Red");
            gryffindor.setColor2("Gold");
            houseRepository.save(gryffindor);

            House slytherin = new House();
            slytherin.setName("Slytherin");
            slytherin.setFounder("Salazar Slytherin");
            slytherin.setColor1("Green");
            slytherin.setColor2("Silver");
            houseRepository.save(slytherin);

            House hufflepuff = new House();
            hufflepuff.setName("HufflePuff");
            hufflepuff.setFounder("Helga Hufflepuff");
            hufflepuff.setColor1("Yellow");
            hufflepuff.setColor2("Black");
            houseRepository.save(hufflepuff);

            House ravenclaw = new House();
            ravenclaw.setName("Ravenclaw");
            ravenclaw.setFounder("Rowena Ravenclaw");
            ravenclaw.setColor1("Blue");
            ravenclaw.setColor2("Bronze");
            houseRepository.save(ravenclaw);

    }

    private Teacher createTeacher() {
        Teacher teacher = new Teacher();
        teacher.setFirstName("Severus");
        teacher.setMiddleName("");
        teacher.setLastName("Snape");
        teacher.setDateOfBirth(LocalDate.of(1960, 1, 9));
        teacher.setHouse((House) houseRepository.findByName("Slytherin").orElse(null));
        teacher.setHeadOfHouse(true);
        teacher.setEmployment(EmploymentType.TENURED);
        teacher.setEmploymentStart(LocalDate.of(1981, 1, 23));
        teacher.setEmploymentEnd(LocalDate.of(1995, 6, 15));


        teacherRepository.save(teacher);
        return teacher;
    }

    private Set<Student> createStudents() {
        Set<Student> students = new HashSet<>();

        students.add(createStudent("Harry", "James", "Potter", LocalDate.of(1980, 7, 31), (House) houseRepository.findByName("Gryffindor").orElse(null), false, 6, 1991, 1998, true));
        students.add(createStudent("Hermione", "Jean", "Granger", LocalDate.of(1979, 9, 19), (House) houseRepository.findByName("Gryffindor").orElse(null), true, 6, 1991, 1998, true));
        students.add(createStudent("Ron", "Bilius", "Weasley", LocalDate.of(1980, 3, 1), (House) houseRepository.findByName("Gryffindor").orElse(null), false, 6, 1991, 1998, true));
        students.add(createStudent("Draco", "Lucius", "Malfoy", LocalDate.of(1980, 6, 5), (House) houseRepository.findByName("Slytherin").orElse(null), false, 6, 1991, 1998, false));
        students.add(createStudent("Neville", "Samwise", "Longbottom", LocalDate.of(1980, 7, 30), (House) houseRepository.findByName("Gryffindor").orElse(null), false, 6, 1991, 1998, true));
        students.add(createStudent("Luna", null, "Lovegood", LocalDate.of(1981, 2, 13), (House) houseRepository.findByName("Ravenclaw").orElse(null), false, 5, 1992, 1999, true));
        students.add(createStudent("Ginny", "Molly", "Weasley", LocalDate.of(1981, 8, 11), (House) houseRepository.findByName("Gryffindor").orElse(null), true, 5, 1992, 1999, true));
        students.add(createStudent("Fred", null, "Weasley", LocalDate.of(1978, 4, 1), (House) houseRepository.findByName("Gryffindor").orElse(null), false, 9, 1989, 1996, true));
        students.add(createStudent("George", null, "Weasley", LocalDate.of(1978, 4, 1), (House) houseRepository.findByName("Gryffindor").orElse(null), false, 9, 1989, 1996, true));
        students.add(createStudent("Cho", null, "Chang", LocalDate.of(1979, 9, 30), (House) houseRepository.findByName("Ravenclaw").orElse(null), false, 6, 1991, 1998, true));

        studentRepository.saveAll(students);
        return students;
    }

    private Student createStudent(String firstName, String middleName, String lastName, LocalDate dateOfBirth, House house, boolean prefect, int schoolYear, int enrollmentYear, int graduationYear, boolean graduated) {
        Student student = new Student();
        student.setFirstName(firstName);
        student.setMiddleName(middleName);
        student.setLastName(lastName);
        student.setDateOfBirth(dateOfBirth);
        student.setHouse(house);
        student.setPrefect(prefect);
        student.setSchoolYear(schoolYear);
        student.setEnrollmentYear(enrollmentYear);
        student.setGraduationYear(graduationYear);
        student.setGraduated(graduated);
        return student;
    }

    private void createCourse(Teacher teacher, Set<Student> students) {
        Course course = new Course();
        course.setSubject("Potions");
        course.setSchoolYear(2024);
        course.setCurrent(true);
        course.setTeacher(teacher);
        course.setStudents(students);
        courseRepository.save(course);
    }
}
