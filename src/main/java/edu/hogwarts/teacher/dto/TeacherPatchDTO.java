package edu.hogwarts.teacher.dto;

import edu.hogwarts.teacher.model.EmploymentType;

import java.time.LocalDate;
import java.util.Date;

public class TeacherPatchDTO {
    private boolean headOfHouse;
    private LocalDate employmentEnd;
    private EmploymentType employment;

    public boolean isHeadOfHouse() {
        return headOfHouse;
    }

    public void setHeadOfHouse(boolean headOfHouse) {
        this.headOfHouse = headOfHouse;
    }

    public LocalDate getEmploymentEnd() {
        return employmentEnd;
    }

    public void setEmploymentEnd(LocalDate employmentEnd) {
        this.employmentEnd = employmentEnd;
    }

    public EmploymentType getEmployment() {
        return employment;
    }

    public void setEmployment(EmploymentType employment) {
        this.employment = employment;
    }
}
