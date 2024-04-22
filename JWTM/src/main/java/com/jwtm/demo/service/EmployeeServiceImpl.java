package com.jwtm.demo.service;


import com.jwtm.demo.model.Employee;
import com.jwtm.demo.repositories.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service

public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private  EmployeeRepository employeeRepository;

    @Autowired
    private  PasswordEncoder passwordEncoder;

    @Override
    public Employee registerEmployee(Employee employee) {
        String password = employee.getPassword();
        employee.setPassword(passwordEncoder.encode(password));
        return employeeRepository.save(employee);
    }
}
