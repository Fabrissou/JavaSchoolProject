package model;

import java.util.List;

public class DepartmentCard {
    String name, hierarchy;
    long department_id;
    EmployeeCard director;
    List<EmployeeCard> departmentEmployees;

    public EmployeeCard getDirector() {
        return director;
    }

    public void setDirector(EmployeeCard director) {
        this.director = director;
    }

    public long getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(long department_id) {
        this.department_id = department_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public DepartmentCard(String name, EmployeeCard director, String hierarchy, List<EmployeeCard> departmentEmployees) {
        this.name = name;
        this.director = director;
        this.hierarchy = hierarchy;
        this.departmentEmployees = departmentEmployees;
    }

    public DepartmentCard() {

    }

    @Override
    public String toString() {
        return "DepartmentCard{" +
                "name='" + name + '\'' +
                ", hierarchy='" + hierarchy + '\'' +
                ", department_id=" + department_id +
                ", director=" + director +
                ", departmentEmployees=" + departmentEmployees +
                '}';
    }
}
