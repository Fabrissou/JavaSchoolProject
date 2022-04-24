package org.javaschool.data.repository;

import org.javaschool.data.model.employee.Position;
import org.springframework.data.repository.CrudRepository;

public interface PositionsRepository extends CrudRepository<Position, Long> {
    Position findByEmployeePosition(String employeePosition);

}
