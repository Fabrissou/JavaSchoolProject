package org.javaschool.service.service.dto;

import lombok.Data;

@Data
public class EmployeeDto {
    private Long id;
    private String employeeInfo;
    private Long positionId;
    private Long departmentId;

    private String position;
}
