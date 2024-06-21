package com.myspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.myspring.model.Employee;

import java.util.Optional;

//@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
                 Optional<Employee> findByUsername(String username);
}