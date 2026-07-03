package com.qsp.roleAccessed.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qsp.roleAccessed.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {}