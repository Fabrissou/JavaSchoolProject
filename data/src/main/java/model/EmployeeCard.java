package model;

import java.util.Objects;

public class EmployeeCard {
    private String personalData, department, position;
    private Long position_id, department_id;

    public String getPersonalData() {
        return personalData;
    }
    public String getDepartment() {
        return department;
    }
    public String getPosition() {
        return position;
    }
    public Long getPosition_id() {
        return position_id;
    }
    public Long getDepartment_id() {
        return department_id;
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
    public void setPosition_id(Long position_id) {
        this.position_id = position_id;
    }
    public void setDepartment_id(Long department_id) {
        this.department_id = department_id;
    }


    @Override
    public String toString() {
        return "EmployeeCard{" +
                "personalData='" + personalData + '\'' +
                ", department='" + department + '\'' +
                ", position='" + position + '\'' +
                ", position_id=" + position_id +
                ", department_id=" + department_id +
                '}';
    }
}
