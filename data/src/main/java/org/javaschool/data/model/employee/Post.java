package org.javaschool.data.model.employee;

import lombok.Data;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "employee_post")
public class Post {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "employee_post", nullable = false, unique = true)
    private String employeePost;
}
