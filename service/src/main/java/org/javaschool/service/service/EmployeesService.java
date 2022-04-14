package org.javaschool.service.service;

import org.javaschool.data.model.department.Department;
import org.javaschool.data.model.employee.Employee;
import org.javaschool.service.service.dto.EmployeeDto;

import java.util.List;

public interface EmployeesService {

    EmployeeDto get(Long id);

    void save(EmployeeDto employeeDto);

    void delete(Long id);

    void update(EmployeeDto employeeDto, Long id);

    Employee mapperEmployee(EmployeeDto employeeDto);

    EmployeeDto mapperEmployeeDto(Employee employee);

    List<EmployeeDto> getAllEmployeesInDepartment(Department department);

}
