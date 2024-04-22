package com.jwtm.demo.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "employees")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @Column(name = "employee_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long employeeId;

    @Column(name = "email")
    private String email;

    private String password;

    @Column(name = "employee_name")
    private String employeeName;


    @Column(name = "employee_phone")
    private String employeePhone;


    @OneToMany(mappedBy = "employee")
    private Set<SecureToken> tokens;
}
