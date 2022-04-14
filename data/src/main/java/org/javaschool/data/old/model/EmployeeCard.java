package org.javaschool.data.old.model;

public class EmployeeCard {
    private String personalData, department, position;
    private Long positionId, departmentId;

    public String getPersonalData() {
        return personalData;
    }
    public String getDepartment() {
        return department;
    }
    public String getPosition() {
        return position;
    }
    public Long getPositionId() {
        return positionId;
    }
    public Long getDepartmentId() {
        return departmentId;
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
    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }
    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }


    @Override
    public String toString() {
        return "EmployeeCard{" +
                "personalData='" + personalData + '\'' +
                ", department='" + department + '\'' +
                ", position='" + position + '\'' +
                ", position_id=" + positionId +
                ", department_id=" + departmentId +
                '}';
    }
}
