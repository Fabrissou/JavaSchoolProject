package org.javaschool.data.old.model;

import java.util.List;

public class DepartmentCard {
    String name;
    List<EmployeeCard> departmentEmployees;
    long id, parentId, type;

    public long getParentId() {
        return parentId;
    }
    public long getId() {
        return id;
    }
    public long getType() {
        return type;
    }
    public String getName() {
        return name;
    }
    public List<EmployeeCard> getDepartmentEmployees() {
        return departmentEmployees;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setParentId(long parentId) {
        this.parentId = parentId;
    }
    public void setType(long type) {
        this.type = type;
    }
    public void setId(long id) {
        this.id = id;
    }
    public void setDepartmentEmployees(List<EmployeeCard> departmentEmployees) {
        this.departmentEmployees = departmentEmployees;
    }
}
