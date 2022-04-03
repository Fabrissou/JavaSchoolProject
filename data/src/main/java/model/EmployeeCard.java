package model;

import java.util.Objects;

public class EmployeeCard {
    private String personalData, department, position;


    public String getPersonalData() {
        return personalData;
    }
    public String getDepartment() {
        return department;
    }
    public String getPosition() {
        return position;
    }


    public void setPersonalData(String personalData) {
        this.personalData = personalData;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "EmployeeCard{" +
                "personalData='" + personalData + '\'' +
                ", department='" + department + '\'' +
                ", position='" + position + '\'' +
                '}';
    }
}
