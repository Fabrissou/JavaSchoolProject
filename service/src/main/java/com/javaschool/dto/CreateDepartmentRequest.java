package com.javaschool.dto;

import model.EmployeeCard;

import java.util.List;

public class CreateDepartmentRequest {
    String name, path;
    long id, parent_id;
    List<CreateEmployeeRequest> departmentEmployees;

    public long getParent_id() { return parent_id; }
    public long getId() { return id; }
    public String getName() {
        return name;
    }
    public String getPath() {
        return path;
    }
//    public CreateEmployeeRequest getDirector() {
//        if (departmentEmployees != null) {
//            for (CreateEmployeeRequest employee: departmentEmployees) {
//                if ("director".equals(employee.getPosition())) {
//                    return employee;
//                }
//            }
//        }
//
//        return null;
//    }
    public List<CreateEmployeeRequest> getDepartmentEmployees() {
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
    public void setDepartmentEmployees(List<CreateEmployeeRequest> departmentEmployees) {this.departmentEmployees = departmentEmployees;}


    @Override
    public String toString() {
        return "CreateDepartmentRequest{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", departmentEmployees=" + departmentEmployees +
                ", id=" + id +
                ", parent_id=" + parent_id +
                '}';
    }
}
