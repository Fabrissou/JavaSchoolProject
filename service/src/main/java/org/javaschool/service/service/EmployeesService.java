package org.javaschool.service.service;

import org.javaschool.data.model.department.Department;
import org.javaschool.data.model.employee.Employee;
import org.javaschool.service.service.dto.EmployeeDto;

import java.util.List;

public interface EmployeesService {

    EmployeeDto get(Long id);

    boolean save(EmployeeDto employeeDto);

    boolean moderatorDelete(Long id);

    boolean adminDelete(Long id);

    boolean update(EmployeeDto employeeDto, Long id);

    Employee mapperEmployee(EmployeeDto employeeDto);

    EmployeeDto mapperEmployeeDto(Employee employee);

    List<EmployeeDto> getAllEmployeesInDepartment(Department department);

}
