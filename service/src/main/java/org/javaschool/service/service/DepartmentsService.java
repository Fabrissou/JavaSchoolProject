package org.javaschool.service.service;


import org.javaschool.data.model.department.Department;
import org.javaschool.service.service.dto.DepartmentDto;
import org.springframework.stereotype.Service;

public interface DepartmentsService {

    DepartmentDto get(Long id);

    void save(DepartmentDto departmentDto);

    void delete(Long id);

    void update(DepartmentDto departmentDto, Long id);

    Department mapperDepartment(DepartmentDto departmentDto);

    DepartmentDto mapperDepartmentDto(Department department);
}
