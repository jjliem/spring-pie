package com.multiverse.springpie.service;

import com.multiverse.springpie.model.Employee;
import com.multiverse.springpie.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class EmployeeAuthorizationService {

  @Autowired
  EmployeeRepository employeeRepository;
  
  public boolean canUpdate(long loggedInUser, long userId){
    if(loggedInUser != userId)
      return false;

    Optional<Employee> optional = employeeRepository.findById(userId);
    if(!optional.isPresent())
      return false;

    Employee inDB = optional.get();
    LocalDateTime twentyFourHoursAgo = LocalDateTime.now().minusHours(24);
    if(inDB.getLastUpdated() != null && inDB.getLastUpdated().isAfter(twentyFourHoursAgo))
      return false;

    return true;
  }
}
