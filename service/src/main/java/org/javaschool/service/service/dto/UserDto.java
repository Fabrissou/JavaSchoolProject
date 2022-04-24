package org.javaschool.service.service.dto;

import lombok.Data;

import java.util.Set;

@Data
public class UserDto {
    private Long id;
    private String password;
    private String role;
    private String username;
    private Long employeeId;
    private Set<Long> validDepartments;
}
