package org.javaschool.service.service.dto;

import lombok.Data;

import java.util.List;

@Data
public class DepartmentDto {
    private Long id;
    private String departmentName;
    private Long parentDepartmentId;
    private Long departmentTypeId;
    private List<EmployeeDto> employeeDtoList;

    private String departmentType;
}
