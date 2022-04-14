package org.javaschool.service.old.dto;

import java.util.List;

public class CreateDepartmentRequest {
    String name;
    long id, type, parentId;
    List<CreateEmployeeRequest> departmentEmployees;

    public long getParentId() { return parentId; }
    public long getId() { return id; }
    public long getType() {
        return type;
    }
    public String getName() {
        return name;
    }
    public List<CreateEmployeeRequest> getDepartmentEmployees() {
        return departmentEmployees;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setType(long type) {
        this.type = type;
    }
    public void setParentId(long parentId) {this.parentId = parentId; }
    public void setId(long id) {this.id = id; }
    public void setDepartmentEmployees(List<CreateEmployeeRequest> departmentEmployees) {this.departmentEmployees = departmentEmployees;}

    @Override
    public String toString() {
        return "CreateDepartmentRequest{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", type=" + type +
                ", parentId=" + parentId +
                ", departmentEmployees=" + departmentEmployees +
                '}';
    }
}
