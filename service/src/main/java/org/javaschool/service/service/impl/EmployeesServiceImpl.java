package org.javaschool.service.service.impl;

import org.javaschool.data.model.department.Department;
import org.javaschool.data.model.employee.Employee;
import org.javaschool.data.model.employee.Position;
import org.javaschool.service.service.DepartmentsService;
import org.javaschool.service.service.EmployeesService;
import org.javaschool.data.repository.EmployeesRepository;
import org.javaschool.service.service.PositionService;
import org.javaschool.service.service.dto.EmployeeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeesServiceImpl implements EmployeesService {

    @Autowired
    private EmployeesRepository employeesRepository;

    @Transactional
    @Override
    public EmployeeDto get(Long id) {
        EmployeeDto employeeDto = null;
        Optional<Employee> optionalEmployee = employeesRepository.findById(id);

        if (optionalEmployee.isPresent()) {
            employeeDto = mapperEmployeeDto(optionalEmployee.get());
        }

        return employeeDto;
    }

    @Transactional
    @Override
    public void save(EmployeeDto employeeDto) {
        if (!employeesRepository.existsById(employeeDto.getId())) {
            employeesRepository.save(mapperEmployee(employeeDto));
        }
    }

    @Transactional
    @Override
    public void delete(Long id) {
        if (employeesRepository.existsById(id)) {
            employeesRepository.deleteById(id);
        }
    }

    @Transactional
    @Override
    public void update(EmployeeDto employeeDto, Long id) {
        if (employeesRepository.existsById(id)) {
            employeeDto.setId(id);
            employeesRepository.save(mapperEmployee(employeeDto));
        }
    }

    @Transactional
    @Override
    public List<EmployeeDto> getAllEmployeesInDepartment(Department department) {
        List<EmployeeDto> employeeDtoList = null;

        if (department != null) {
            employeeDtoList = new ArrayList<>();
            List<Employee> allByDepartmentId = employeesRepository.findAllByDepartmentId(department);

            for (Employee employee: allByDepartmentId) {
                employeeDtoList.add(mapperEmployeeDto(employee));
            }
        }

        return employeeDtoList;
    }

    @Transactional
    @Override
    public Employee mapperEmployee(EmployeeDto employeeDto) {
        Employee employee = null;

        if (employeeDto != null) {
            employee = new Employee();
            Department department = new Department();
            Position position = new Position();
            department.setId(employeeDto.getDepartmentId());
            position.setId(employeeDto.getPositionId());

            employee.setId(employeeDto.getId());
            employee.setEmployeeInfo(employeeDto.getEmployeeInfo());
            employee.setPositionId(position);
            employee.setDepartmentId(department);
        }

        return employee;
    }

    @Transactional
    @Override
    public EmployeeDto mapperEmployeeDto(Employee employee) {
        EmployeeDto employeeDto = null;

        if (employee != null) {
            employeeDto = new EmployeeDto();

            employeeDto.setId(employee.getId());
            employeeDto.setEmployeeInfo(employee.getEmployeeInfo());
            employeeDto.setPositionId(employee.getPositionId().getId());
            employeeDto.setDepartmentId(employee.getDepartmentId().getId());
        }

        return employeeDto;
    }
}
