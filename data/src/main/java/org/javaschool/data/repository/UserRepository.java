package org.javaschool.data.repository;

import org.javaschool.data.model.employee.Employee;
import org.javaschool.data.model.role.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmployeeId(Employee employeeId);
    User findByUsername(String username);
}
