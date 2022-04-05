package model;

import java.util.List;
import java.util.Objects;

public class DepartmentCard {
    String name, path;
    List<EmployeeCard> departmentEmployees;
    long id, parent_id;

    public long getParent_id() { return parent_id; }
    public long getId() { return id; }
    public String getName() {
        return name;
    }
    public String getPath() {
        return path;
    }
    public EmployeeCard getDirector() {
        if (departmentEmployees != null) {
            for (EmployeeCard employee: departmentEmployees) {
                if ("director".equals(employee.getPosition())) {
                    return employee;
                }
            }
        }

        return null;
    }
    public List<EmployeeCard> getDepartmentEmployees() {
        return departmentEmployees;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public void setParent_id(long parent_id) {this.parent_id = parent_id; }
    public void setId(long id) {this.id = id; }
    public void setDepartmentEmployees(List<EmployeeCard> departmentEmployees) {this.departmentEmployees = departmentEmployees;}
}
