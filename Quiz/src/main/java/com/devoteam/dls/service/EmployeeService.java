package com.devoteam.dls.service;

import java.util.List;

import com.devoteam.dls.domain.Employee;

/**
 * This Service layer contains Business logic methods.
 * They must be separated from EmployeeRepository, which is ONLY responsible of Data Access.
 * 
 * 	@author Devoteam Munich (Besmir Beka, Bastien Thibaud).
 *  @version 1.0.
 *  @since 1.0.
 *  @see EmployeeServiceImpl
 *
 */

public interface EmployeeService {
	
	void createEmployee(Employee employee);
	
	List<Employee> fetchAllEmployees();
	
	List<Employee> fetchAllLoggedInEmployee();
}
