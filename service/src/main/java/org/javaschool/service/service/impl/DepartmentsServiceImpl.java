package org.javaschool.service.service.impl;

import org.javaschool.data.model.department.Department;
import org.javaschool.data.model.department.Type;
import org.javaschool.data.repository.EmployeesRepository;
import org.javaschool.service.service.DepartmentsService;
import org.javaschool.data.repository.DepartmentsRepository;
import org.javaschool.service.service.EmployeesService;
import org.javaschool.service.service.TypesService;
import org.javaschool.service.service.dto.DepartmentDto;
import org.javaschool.service.service.dto.EmployeeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentsServiceImpl implements DepartmentsService {

    @Autowired
    private DepartmentsRepository departmentsRepository;

    @Autowired
    private EmployeesRepository employeesRepository;

    @Autowired
    private EmployeesService employeesService;


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
    public void save(DepartmentDto departmentDto) {
        if (!departmentsRepository.existsById(departmentDto.getId())) {
            departmentsRepository.save(mapperDepartment(departmentDto));
        }
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Department departmentToDelete = mapperDepartment(get(id));
        del(departmentToDelete);
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
    public void update(DepartmentDto departmentDto, Long id) {
        if (departmentsRepository.existsById(id)) {
            departmentDto.setId(id);
            departmentsRepository.save(mapperDepartment(departmentDto));
        }
    }

    @Transactional
    @Override
    public Department mapperDepartment(DepartmentDto departmentDto) {
        Department department = null;

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
            departmentDto.setParentDepartment(department.getParentDepartment().getId());
            departmentDto.setEmployeeDtoList(employeesService.getAllEmployeesInDepartment(department));
        }

        return departmentDto;
    }
}
