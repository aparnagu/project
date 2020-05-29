package com.example.restfuldemo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.restfuldemo.model.Department;
import com.example.restfuldemo.model.Employee;
import com.example.restfuldemo.repository.DepartmentRepository;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class DepartmentController {
	
	@Autowired
	DepartmentRepository departmentRepository;

	@GetMapping("/departments")
	public ResponseEntity<List<Department>> getAllDepartments(@RequestParam(required = false) String empname) {
		try {
			List<Department> departments = new ArrayList<Department>();

			if (empname == null)
				departmentRepository.findAll().forEach(departments::add);
			else
				departmentRepository.findByName(empname).forEach(departments::add);

			if (departments.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(departments, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("Exception in getAllDepartments==>"+e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/departments/{id}")
	public ResponseEntity<Department> getDepartmentById(@PathVariable("id") long id) {
		Optional<Department> departmentData = departmentRepository.findById(id);

		if (departmentData.isPresent()) {
			return new ResponseEntity<>(departmentData.get(), HttpStatus.OK);
		} else {
			System.out.println("Department not found to retrieve");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/employeesbydepartments/{id}")
	public ResponseEntity<List<Employee>> getEmployeesbyDepartmentById(@PathVariable("id") long id) {
		Optional<Department> departmentData = departmentRepository.findById(id);

		if (departmentData.isPresent()) {
			return new ResponseEntity<>(departmentData.get().getEmployees(), HttpStatus.OK);
		} else {
			System.out.println("Department not found to retrieve");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/departmentssorted")
	public ResponseEntity<List<String>> getDepartmentsList() {
		try {
			List<String> departments = new ArrayList<String>();

			departments = departmentRepository.findAll().stream().map(Department::getName).distinct().sorted()
					.collect(Collectors.toList());

			if (departments.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(departments, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("Exception in getDepartmentsList==>"+e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/departments")
	public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
		try {
			Department _department = departmentRepository.save(department);
			return new ResponseEntity<>(_department, HttpStatus.CREATED);
		} catch (Exception e) {
			System.out.println("Exception in createDepartment==>"+e);
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
	}

	@PutMapping("/departments/{id}")
	public ResponseEntity<Department> updateDepartment(@PathVariable("id") long id,
			@RequestBody Department department) {
		Optional<Department> departmentData = departmentRepository.findById(id);

		if (departmentData.isPresent()) {
			Department _department = departmentData.get();
			_department.setName(department.getName());
			return new ResponseEntity<>(departmentRepository.save(_department), HttpStatus.OK);
		} else {
			System.out.println("Department not found to update");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/departments/{id}")
	public ResponseEntity<HttpStatus> deleteDepartment(@PathVariable("id") long id) {
		try {
			departmentRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			System.out.println("Exception in deleteDepartment==>"+e);
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}

	@DeleteMapping("/departments")
	public ResponseEntity<HttpStatus> deleteAllDepartments() {
		try {
			departmentRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			System.out.println("Exception in deleteAllDepartments==>"+e);
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}

	}

}
