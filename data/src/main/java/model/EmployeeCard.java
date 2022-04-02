package model;

import java.util.Objects;

public class EmployeeCard {
    private long id;
    private String personalData, department, position;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPersonalData() {
        return personalData;
    }

    public void setPersonalData(String personalData) {
        this.personalData = personalData;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public EmployeeCard() {

    }

    public EmployeeCard(long id, String personalData, String position, String department) {
        this.id = id;
        this.personalData = personalData;
        this.position = position;
        this.department = department;
    }

    public EmployeeCard(String personalData, String position, String department) {
        this.personalData = personalData;
        this.position = position;
        this.department = department;
    }

    @Override
    public String toString() {
        return "EmployeeCard{" +
                "id=" + id +
                ", personalData='" + personalData + '\'' +
                ", department='" + department + '\'' +
                ", position='" + position + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeCard that = (EmployeeCard) o;
        return id == that.id && Objects.equals(personalData, that.personalData) && Objects.equals(department, that.department) && Objects.equals(position, that.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, personalData, department, position);
    }
}
