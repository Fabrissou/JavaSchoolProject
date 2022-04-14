package org.javaschool.data.repository;

import org.javaschool.data.model.department.Department;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DepartmentsRepository extends CrudRepository<Department, Long> {
    List<Department> findAllByParentDepartment(Department parentDepartment);
}
