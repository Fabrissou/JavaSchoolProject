package org.javaschool.data.repository;

import liquibase.pro.packaged.L;
import org.javaschool.data.model.department.Department;
import org.javaschool.data.model.employee.Employee;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EmployeesRepository extends CrudRepository<Employee, Long> {
    List<Employee> findAllByDepartmentId(Department departmentId);


    @Transactional
    @Modifying
    @Query("update Employee employee set employee.departmentId = :newDepartment where employee.departmentId = :oldDepartment")
    void changeDepartment(@Param("oldDepartment") Department oldDepartment, @Param("newDepartment") Department newDepartment);
}
