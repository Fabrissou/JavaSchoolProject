package model;

import java.util.List;

public class DepartmentCard {
    String name, director, hierarchy;
    List<EmployeeCard> departmentEmployees;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getHierarchy() {
        return hierarchy;
    }

    public void setHierarchy(String hierarchy) {
        this.hierarchy = hierarchy;
    }

    public List<EmployeeCard> getDepartmentEmployees() {
        return departmentEmployees;
    }

    public void setDepartmentEmployees(List<EmployeeCard> departmentEmployees) {
        this.departmentEmployees = departmentEmployees;
    }

    public DepartmentCard(String name, String director, String hierarchy, List<EmployeeCard> departmentEmployees) {
        this.name = name;
        this.director = director;
        this.hierarchy = hierarchy;
        this.departmentEmployees = departmentEmployees;
    }
}
