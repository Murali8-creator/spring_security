package com.jwtm.demo.controller;


import com.jwtm.demo.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwtm.demo.service.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    private final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @PostMapping("/register")
    public ResponseEntity<SuccessResponse<Employee>> register(@RequestBody Employee employee){
        Employee registeredEmployee = employeeService.registerEmployee(employee);
        logger.info("Employee registered successfully");
        return ResponseEntity.ok(new SuccessResponse<>(registeredEmployee));
    }
}
