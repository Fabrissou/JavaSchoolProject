package org.javaschool.service.service.impl;

import org.javaschool.data.model.department.Department;
import org.javaschool.data.model.department.Type;
import org.javaschool.data.model.employee.Position;
import org.javaschool.data.repository.EmployeesRepository;
import org.javaschool.data.repository.PositionsRepository;
import org.javaschool.data.repository.TypesRepository;
import org.javaschool.service.service.DepartmentsService;
import org.javaschool.data.repository.DepartmentsRepository;
import org.javaschool.service.service.EmployeesService;
import org.javaschool.service.service.TypesService;
import org.javaschool.service.service.dto.DepartmentDto;
import org.javaschool.service.service.dto.EmployeeDto;
import org.javaschool.service.service.mail.NotificationGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class DepartmentsServiceImpl implements DepartmentsService {
    @Autowired
    private NotificationGenerator notificationGenerator;

    @Autowired
    private DepartmentsRepository departmentsRepository;

    @Autowired
    private EmployeesRepository employeesRepository;

    @Autowired
    private TypesRepository typesRepository;

    @Autowired
    private EmployeesService employeesService;

    @Autowired
    private PositionsRepository positionsRepository;

    @Transactional
    @Override
    public DepartmentDto get(Long id) {
        DepartmentDto departmentDto = null;
        Optional<Department> optional = departmentsRepository.findById(id);

        if (optional.isPresent()) {
            departmentDto = mapperDepartmentDto(optional.get());
        }

        return departmentDto;
    }

    @Transactional
    @Override
    public boolean save(DepartmentDto departmentDto) {
        if ((!departmentsRepository.existsById(departmentDto.getId())) &&
                typesRepository.existsById(departmentDto.getDepartmentType()) &&
                departmentsRepository.existsById(departmentDto.getParentDepartment())) {
            departmentsRepository.save(mapperDepartment(departmentDto));
            notificationGenerator.createDepartmentNotification(typesRepository.findById(departmentDto.getDepartmentType()).get().getDepartmentType(), departmentDto.getDepartmentName());
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    @Override
    public boolean delete(Long id) {
        if (departmentsRepository.existsById(id)) {
            Department departmentToDelete = mapperDepartment(get(id));
            del(departmentToDelete);
            notificationGenerator.deleteDepartmentNotification(id);
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    void del(Department department) {
        List<Department> childrenList = departmentsRepository.findAllByParentDepartment(department);

        childrenList.forEach(this::del);

        employeesRepository.changeDepartment(department, department.getParentDepartment());
        departmentsRepository.delete(department);
    }

    @Transactional
    @Override
    public boolean update(DepartmentDto departmentDto, Long id) {
        if (departmentsRepository.existsById(id) &&
                departmentsRepository.existsById(departmentDto.getParentDepartment()) &&
                typesRepository.existsById(departmentDto.getDepartmentType())) {
            departmentDto.setId(id);
            departmentsRepository.save(mapperDepartment(departmentDto));
            notificationGenerator.updateDepartmentNotification(id);
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    @Override
    public Department mapperDepartment(DepartmentDto departmentDto) {
        Department department = null;
        System.out.println(departmentDto);
        if (departmentDto != null) {
            department = new Department();
            Department parentDepartment = new Department();
            Type departmentType = new Type();
            parentDepartment.setId(departmentDto.getParentDepartment());
            departmentType.setId(departmentDto.getDepartmentType());

            department.setId(departmentDto.getId());
            department.setDepartmentName(departmentDto.getDepartmentName());
            department.setParentDepartment(parentDepartment);
            department.setDepartmentType(departmentType);
        }

        return department;
    }

    @Transactional
    @Override
    public DepartmentDto mapperDepartmentDto(Department department) {
        DepartmentDto departmentDto = null;

        if (department != null) {
            departmentDto = new DepartmentDto();
            departmentDto.setId(department.getId());
            departmentDto.setDepartmentName(department.getDepartmentName());
            departmentDto.setDepartmentType(department.getDepartmentType().getId());
            if (department.getParentDepartment() == null) {
                departmentDto.setParentDepartment(null);
            } else {
                departmentDto.setParentDepartment(department.getParentDepartment().getId());
            }
            departmentDto.setEmployeeDtoList(employeesService.getAllEmployeesInDepartment(department));
        }

        return departmentDto;
    }

    @Transactional
    public void addHierarchyToSet(Set<Long> set, Department department) {
        List<Department> childrenList = departmentsRepository.findAllByParentDepartment(department);

        for (Department d: childrenList) {
            this.addHierarchyToSet(set, d);
        }

        set.add(department.getId());
    }

    @Transactional
    @Override
    public boolean checkDirector(Long departmentId) {
        Position director = positionsRepository.findByEmployeePosition("director");
        Department department = departmentsRepository.findById(departmentId).get();

        return employeesRepository.countAllByDepartmentIdAndPositionId(department, director) > 0;
    }
}
