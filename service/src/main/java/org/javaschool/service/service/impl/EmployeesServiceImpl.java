package org.javaschool.service.service.impl;

import org.javaschool.data.model.department.Department;
import org.javaschool.data.model.employee.Employee;
import org.javaschool.data.model.employee.Position;
import org.javaschool.data.model.role.User;
import org.javaschool.data.repository.DepartmentsRepository;
import org.javaschool.data.repository.PositionsRepository;
import org.javaschool.data.repository.UserRepository;
import org.javaschool.service.service.DepartmentsService;
import org.javaschool.service.service.EmployeesService;
import org.javaschool.data.repository.EmployeesRepository;
import org.javaschool.service.service.PositionService;
import org.javaschool.service.service.dto.EmployeeDto;
import org.javaschool.service.service.mail.NotificationGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EmployeesServiceImpl implements EmployeesService {
    @Autowired
    private NotificationGenerator notificationGenerator;

    @Autowired
    private EmployeesRepository employeesRepository;

    @Autowired
    private PositionsRepository positionsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentsRepository departmentsRepository;

    @Autowired
    private DepartmentsService departmentsService;

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
    public boolean save(EmployeeDto employeeDto) {
        if ((!employeesRepository.existsById(employeeDto.getId())) &&
                (positionsRepository.existsById(employeeDto.getPositionId())) &&
                (departmentsRepository.existsById(employeeDto.getDepartmentId())) ) {

            Employee employeeToSave = mapperEmployee(employeeDto);

            if (Objects.equals(positionsRepository.findById(employeeDto.getPositionId()).get().getEmployeePosition(), "director")) {
                if (departmentsService.checkDirector(employeeDto.getDepartmentId())) {
                    return false;
                } else {
                    employeesRepository.save(employeeToSave);
                    Employee director = employeesRepository.findByDepartmentIdAndPositionId(employeeToSave.getDepartmentId(), employeeToSave.getPositionId());
                    userRepository.save(createDefaultModerator(director));
                    notificationGenerator.createEmployeeNotification(departmentsRepository.findById(employeeToSave.getDepartmentId().getId()).get());
                    return true;
                }
            }
            notificationGenerator.createEmployeeNotification(departmentsRepository.findById(employeeToSave.getDepartmentId().getId()).get());
            employeesRepository.save(employeeToSave);
            return true;
        }

        return false;
    }

    @Transactional
    @Override
    public boolean moderatorDelete(Long id) {

        if (employeesRepository.existsById(id)) {
            User userToDelete = userRepository.findByEmployeeId(employeesRepository.findById(id).get());
            System.out.println(userToDelete);
            if (userToDelete != null) {
                if ("ROLE_USER".equals(userToDelete.getRole())) {
                    userRepository.delete(userToDelete);
                    employeesRepository.deleteById(id);
                    notificationGenerator.deleteEmployeeNotification(id);
                    return true;
                }
            } else {
                employeesRepository.deleteById(id);
                notificationGenerator.deleteEmployeeNotification(id);
                return true;
            }
        }

        return false;
    }

    @Transactional
    @Override
    public boolean adminDelete(Long id) {
        if (employeesRepository.existsById(id)) {
            User userToDelete = userRepository.findByEmployeeId(employeesRepository.findById(id).get());
            if (userToDelete != null) {
                userRepository.delete(userToDelete);
            }
            employeesRepository.deleteById(id);
            notificationGenerator.deleteEmployeeNotification(id);
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public boolean update(EmployeeDto employeeDto, Long id) {
        if (employeesRepository.existsById(id) &&
                departmentsRepository.existsById(employeeDto.getDepartmentId()) &&
                positionsRepository.existsById(employeeDto.getPositionId())) {
            employeeDto.setId(id);
            Employee employeeToUpdate = employeesRepository.findById(id).get();
            Employee updatedEmployee = mapperEmployee(employeeDto);


            if (Objects.equals(positionsRepository.findById(employeeDto.getPositionId()).get().getEmployeePosition(), "director")) {
                if (employeeToUpdate.getPositionId().getEmployeePosition().equals("director")) {
                    User moderatorToUpdate = userRepository.findByUsername(employeeToUpdate.getEmployeeInfo());
                    moderatorToUpdate.setUsername(updatedEmployee.getEmployeeInfo());
                    userRepository.save(moderatorToUpdate);
                    employeesRepository.save(updatedEmployee);
                    notificationGenerator.updateEmployeeNotification(id);
                    return true;
                }

                if (departmentsService.checkDirector(employeeToUpdate.getDepartmentId().getId())) {
                    return false;
                } else {
                    employeesRepository.save(updatedEmployee);
                    Employee director = employeesRepository.findByDepartmentIdAndPositionId(updatedEmployee.getDepartmentId(), updatedEmployee.getPositionId());
                    userRepository.save(createDefaultModerator(director));
                    notificationGenerator.updateEmployeeNotification(id);
                    return true;
                }
            }


            employeesRepository.save(mapperEmployee(employeeDto));
            notificationGenerator.updateEmployeeNotification(id);
            return true;
        }

        return false;
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
            employeeDto.setPosition(employee.getPositionId().getEmployeePosition());
            employeeDto.setDepartmentId(employee.getDepartmentId().getId());
        }

        return employeeDto;
    }

    @Transactional
    User createDefaultModerator(Employee director) {
        User defaultModerator = new User();
        defaultModerator.setEmployeeId(director);
        defaultModerator.setRole("ROLE_MODERATOR");
        defaultModerator.setUsername(director.getEmployeeInfo());
        defaultModerator.setPassword("$2a$10$MsmL9LkIvVlxXIPOZhJJiu.3g4b/EdKmxdWt6DTT47mkTn56EE/6.");
        return defaultModerator;
    }
}
