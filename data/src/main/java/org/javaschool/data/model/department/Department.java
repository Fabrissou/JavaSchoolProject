package org.javaschool.data.model.department;

import lombok.Data;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "department_name", nullable = false)
    private String departmentName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_department", nullable = true)
    private Department parentDepartment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_type", nullable = false, updatable = false)
    private Type departmentType;
}
