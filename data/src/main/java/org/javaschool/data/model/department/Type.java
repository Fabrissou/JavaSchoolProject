package org.javaschool.data.model.department;


import lombok.Data;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "department_types")
public class Type {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "department_type", nullable = false, unique = true)
    private String departmentType;
}
