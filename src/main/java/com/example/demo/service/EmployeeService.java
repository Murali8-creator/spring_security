package com.example.demo.service;


import com.example.demo.model.Employee;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Service
public class EmployeeService {

    private final List<Employee> employees = new ArrayList<>();

    public EmployeeService() {
        employees.add(new Employee(UUID.randomUUID().toString(), "murali", "murali@gmail.com"));
        employees.add(new Employee(UUID.randomUUID().toString(), "sai", "sai@gmail.com"));
    }


}
