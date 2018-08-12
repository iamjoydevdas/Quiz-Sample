package com.devoteam.dls.domain;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * 
 * <p>
 * Persisted instances of this class will be represented as one row in the role table
 * (Database table).
 * </p>
 *  
 * <p>
 * This class will represented the role (user role or administrator role) that have each user (employee). 
 * The entity has an attribute that references to employee (many to one relationship). 
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
 * {@link JoinColumns} - Defines mapping for composite foreign keys. This annotation.
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
 * @author Devoteam Munich,Germany, work students (Besmir Beka, Bastien
 *         Thibaud).
 * @version 1.0.
 * @since 06.08.2018.
 *
 */
@Entity
@Table(name = "role")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LogManager.getLogger(Role.class);


    @Id
    @GeneratedValue
    @Column(name = "role_ID")
    private Long role_ID;

	@Enumerated(value = EnumType.STRING)
	@JoinColumn(name="role_type", nullable = false, unique = true)
    private RoleType roleType;

	@CreatedDate
    @Type(type="java.sql.Timestamp")
    @Column(name = "created_date", updatable = false)
    private Date created_date;


	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "employee_ID", nullable = false, updatable = false)
    private Employee employee;
	
	// Constructors
	
    public Role() {
    	LOG.info("Default constructor role create {}", this.getClass());
    }

    	
    // Getters and Setters
    
	public Long getRole_ID() {
		LOG.info("Method getRole_ID from class role invoked {}");
        return this.role_ID;
    }

    public void setRole_ID(Long id) {
    	LOG.info("Method setRole_ID from class role invoked {}");
        this.role_ID = id;
    }

    public RoleType getRoleType() {
    	LOG.info("Method getRoleType from class role invoked {}");
        return this.roleType; 
    }

    public void setRoleType(RoleType type) {
    	LOG.info("Method setRoleType from class role invoked {}");
        this.roleType = type;
    }

    public Date getCreated_date() { 
    	LOG.info("Method getCreated_date from class role invoked {}");
        return this.created_date;
    }

    public void setCreated_date(Date createdDate) {
    	LOG.info("Method setCreated_date from class role invoked {}");
        this.created_date = createdDate;
    }

    public Employee getEmployee() {
    	LOG.info("Method getEmployee from class role invoked {}");
        return this.employee;
    }

    public void setEmployee(Employee user) {
    	LOG.info("Method setEmployee from class role invoked {}");
        this.employee = user;
    }

    // equals, hashCode, toString
    
    @Override
    public boolean equals(Object o) {
    	LOG.info("Method equals from class role invoked");
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;

        Role role = (Role) o;

        if (this.role_ID != null ? !this.role_ID.equals(role.role_ID) : role.role_ID != null) return false;
        if (this.roleType != role.roleType) return false;
        return this.created_date != null ? this.created_date.equals(role.created_date) : role.created_date == null;
    }

    @Override
    public int hashCode() {
    	LOG.info("Method equals from class role invoked");
        int result = role_ID != null ? this.role_ID.hashCode() : 0;
        result = 31 * result + this.roleType.hashCode();
        result = 31 * result + (this.created_date != null ? this.created_date.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
    	LOG.info("Method toString from class role invoked");
        return "Role{" +
                "id=" + this.role_ID +
                ", type=" + this.roleType +
                ", createdDate=" + this.created_date +
                '}';
    }

}
