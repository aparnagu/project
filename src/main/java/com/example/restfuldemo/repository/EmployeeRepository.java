package com.example.restfuldemo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.example.restfuldemo.model.Department;
import com.example.restfuldemo.model.Employee;


public interface EmployeeRepository extends JpaRepository<Employee, Long>{
	
	 List<Employee> findByName(String empname);

}
