package com.example.restfuldemo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.example.restfuldemo.model.Department;


public interface DepartmentRepository extends JpaRepository<Department, Long>{
	
	 List<Department> findByName(String depname);

}
