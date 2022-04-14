package org.javaschool.data.model.employee;


import lombok.Data;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "employee_positions")
public class Position {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "employee_position", nullable = false, unique = true)
    private String employeePosition;
}
