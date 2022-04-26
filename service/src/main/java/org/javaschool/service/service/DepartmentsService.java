package org.javaschool.service.service;


import org.javaschool.data.model.department.Department;
import org.javaschool.service.service.dto.DepartmentDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

public interface DepartmentsService {

    DepartmentDto get(Long id);

    List<DepartmentDto> getAll();

    boolean save(DepartmentDto departmentDto);

    boolean delete(Long id);

    boolean update(DepartmentDto departmentDto, Long id);

    Department mapperDepartment(DepartmentDto departmentDto);

    DepartmentDto mapperDepartmentDto(Department department);

    void addHierarchyToSet(Set<Long> set, Department department);

    public boolean checkDirector(Long departmentId);

}
