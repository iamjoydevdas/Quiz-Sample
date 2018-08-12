/**
 * 
 */
package com.devoteam.dls.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devoteam.dls.Application;
import com.devoteam.dls.dao.EmployeeRepository;
import com.devoteam.dls.domain.Employee;

/**
 * This Service layer contains Business logic methods specifications.
 * It Overrides methods declared in the EmployeeService interface.
 * 
 * 	@author Devoteam Munich (Besmir Beka, Bastien Thibaud).
 *  @version 1.0.
 *  @since 1.0.
 *
 */

@Service
@Transactional(readOnly = true)
public class EmployeeServiceImpl implements EmployeeService {
	
    private static final Logger LOG = LogManager.getLogger(Application.class);
	
	@Autowired
	private EmployeeRepository employeeRepo;

	@Override
	public void createEmployee(Employee employee) {
		// TODO Insert Business Logic here e.g. "Employee already exists"
		LOG.info("trying to save an employee with EmployeeService");
		employeeRepo.save(employee);
		LOG.info("if you can read this, it means IT WORKED");
	}
	
}