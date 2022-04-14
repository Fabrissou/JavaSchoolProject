package org.javaschool.service.service.dto;

import lombok.Data;

import java.util.List;

@Data
public class DepartmentDto {
    private Long id;
    private String departmentName;
    private Long parentDepartment;
    private Long departmentType;
    private List<EmployeeDto> employeeDtoList;
}
