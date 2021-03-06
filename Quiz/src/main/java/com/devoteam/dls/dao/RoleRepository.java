package com.devoteam.dls.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devoteam.dls.domain.Role;

import java.util.List;

/**
 * <p>
 * Extending JpaRepository to get a bunch of generic CRUD methods to create,
 * update, delete etc method. Use for the entity {@link Role}. Query data
 * from the table role in the database.
 * </p>
 * 
 * @see 
 *      <p>
 *      {@link JpaRepository}
 *      </p>
 * 
 * @author Devoteam Munich,Germany, work students (Besmir Beka, Bastien
 *         Thibaud).
 * 
 * @version 1.0.
 * @since 06.08.2018.
 * 
 *
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

	/**
	 * This is a query to find the employee id in the table row 
	 * 
	 * @param userId - the user id in the records of the table role
	 * @return the entity or records Employee from the table employee
	 */
	List<Role> findByEmployee(Long userId);

}
