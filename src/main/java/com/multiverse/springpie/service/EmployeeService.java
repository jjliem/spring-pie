package com.multiverse.springpie.service;

import com.multiverse.springpie.exception.ResourceNotFoundException;
import com.multiverse.springpie.model.Employee;
import com.multiverse.springpie.repository.EmployeeRepository;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

  EmployeeRepository employeeRepository;

  public EmployeeService(EmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
  }

  public Employee save(Employee employee) {
    String hashedPassword = Hasher.hash(employee.getPassword());
    employee.setPassword(hashedPassword);
    return this.employeeRepository.save(employee);
  }

  public Employee updateEmployee(long id, Employee employeeDetails) {
    Employee updateEmployee = employeeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id: " + id));

    updateEmployee.setFirstName(employeeDetails.getFirstName());
    updateEmployee.setLastName(employeeDetails.getLastName());
    updateEmployee.setEmailId(employeeDetails.getEmailId());

    return employeeRepository.save(updateEmployee);
  }

}
