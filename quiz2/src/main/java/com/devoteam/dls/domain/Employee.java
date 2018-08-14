package com.devoteam.dls.domain;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * <p>
 * Persisted instances of this class will be represented as one row in the table
 * (Database table).
 * </p>
 *  
 * <p>
 * This class will represented a user that have role and so on. One Employee
 * will represented a records in the Database.
 * </p>
 *  
 * @see
 * <p>
 * {@link Entity} - Specifies that the class is an entity. This annotation is
 * applied to the entity class.
 * </p>
 * 
 * <p>
 * {@link Table} - Specifies the primary table for the annotated entity.
 * </p>
 * 
 * <p>
 * {@link Id} - Specifies the primary key of an entity.
 * </p>
 * 
 * <p>
 * {@link GeneratedValue} - Provides for the specification of generation
 * strategies for the values of primary keys.
 * </p>
 * 
 * <p>
 * {@link Column} - Is used to specify the mapped column for a persistent
 * property or field.
 * </p>
 * 
 * <p>
 * {@link CreatedDate} - Declares a field as the one representing the date the
 * entity containing the field was created.
 * </p>
 * 
 * <p>
 * {@link Type} - Defines a Hibernate type mapping.
 * </p>
 * 
 * <p>
 * {@link OneToMany} - Defines a many-valued association with one-to-many
 * multiplicity.
 * </p>
 * 
 * @author Devoteam Munich,Ggermany, work students (Besmir Beka, Bastien
 *         Thibaud).
 * @version 1.0.
 * @since 06.08.2018.
 *
 */
@Entity
@Table(name = "employee")
public class Employee implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LogManager.getLogger(Employee.class);

	@Id
	@GeneratedValue
	@Column(name = "employee_ID")
	private Long employee_ID;

	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;

	@CreatedDate
	@Type(type = "java.sql.Timestamp")
	@Column(name = "created_date", updatable = false)
	private Date created_date;

	@CreatedDate
	@Type(type = "java.sql.Timestamp")
	@Column(updatable = false)
	private Date last_modified_date;

	@OneToMany(mappedBy = "employee", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@Column(name = "roles")
	private List<Role> roles;
	
	// Constructors
	
	public Employee() {
		LOG.info("Entity employee created {}", this.getClass());
	}

	public Employee(Long id) {
		LOG.info("Set employee id");
		this.employee_ID = id;
	}
	
	// Getters and Setters

	public Long getEmployee_ID() {
		LOG.info("Method getEmployee_ID from class employee invoked {} ");
		return this.employee_ID;
	}

	public void setEmployee_ID(Long id) {
		LOG.info("Method setEmployee_ID from class employee invoked.");
		this.employee_ID = id;
	}

	public String getUsername() {
		LOG.info("Method getUsername from class employee invoked {} ");
		return this.username;
	}

	public void setUsername(String username) {
		LOG.info("Method setUsername from class employee invoked {}");
		this.username = username;
	}

	public String getPassword() {
		LOG.info("Method getPassword from class employee invoked. {}");
		return this.password;
	}

	public void setPassword(String password) {
		LOG.info("Method setPassword from class employee invoked. {}");
		this.password = password;
	}

	public Date getCreated_date() {
		LOG.info("Method getCreated_date from class employee invoked.  {}");
		return this.created_date;
	}

	public void setCreated_date(Date createdDate) {
		LOG.info("Method setCreated_date from class employee invoked. New date  {}");
		this.created_date = createdDate;
	}

	public Date getLast_modified_date() {
		LOG.info("Method getLast_modified_date from class employee invoked. {}");
		return this.last_modified_date;
	}

	public void setLast_modified_date(Date lastModifiedDate) {
		LOG.info("Method setLast_modified_date from class employee invoked. {}");
		this.last_modified_date = lastModifiedDate;
	}

	public List<Role> getRoles() {
		LOG.info("Method getRoles from class employee invoked. {}");
		return this.roles;
	}

	public void setRoles(List<Role> roles) {
		LOG.info("Method setRoles from class employee invoked. {}");
		this.roles = roles;
	}

	// equals, hashCode, toString
	
	@Override
	public boolean equals(Object o) {

		LOG.info("Method equals from class employee invoked. {}", this.getClass());

		if (this == o)
			return true;
		if (o == null || this.getClass() != o.getClass())
			return false;

		Employee user = (Employee) o;

		if (this.employee_ID != null ? !this.employee_ID.equals(user.employee_ID) : user.employee_ID != null)
			return false;
		if (!this.username.equals(user.username))
			return false;
		if (!this.password.equals(user.password))
			return false;
		if (this.created_date != null ? !this.created_date.equals(user.created_date) : user.created_date != null)
			return false;
		return this.last_modified_date != null ? this.last_modified_date.equals(user.last_modified_date)
				: user.last_modified_date == null;
	}

	@Override
	public int hashCode() {
		int result = this.employee_ID != null ? this.employee_ID.hashCode() : 0;
		result = 31 * result + this.username.hashCode();
		result = 31 * result + this.password.hashCode();
		result = 31 * result + (this.created_date != null ? this.created_date.hashCode() : 0);
		LOG.info("Method hashCode from class employee invoked. {}", result);
		return result;
	}

	@Override
	public String toString() {
		LOG.info("Method toString from class employee invoked. {}", this.getClass());
		return "User{" + "id=" + this.employee_ID + ", username='" + this.username + '\'' + ", password='" + this.password + '\''
				+ ", createdDate=" + this.created_date + ", lastModifiedDate=" + this.last_modified_date + '}';
	}

}
