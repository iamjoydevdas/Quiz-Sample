package com.devoteam.dls.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "quizzers")
public class Quizzer implements Serializable {
	private static final long serialVersionUID = -5565722914872035692L;
	
	@Id
    @GeneratedValue
    @Column(name = "quizzer_ID")
    private Long quizzer_ID;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "emp_ID", nullable = false, updatable = false)
    private Employee employee;
	
    @Type(type="java.sql.Timestamp")
    @Column(name = "last_Played", updatable = false)
    private Date last_Played;
	
    @Type(type="java.lang.Integer")
    @Column(name = "total_Played")
    private Integer total_Played;
    
    @Type(type="java.lang.Integer")
    @Column(name = "total_Win")
    private Integer total_Win;
	
	@Enumerated(value = EnumType.ORDINAL)
	@JoinColumn(name="quizzer_status", nullable = false, unique = false)
    private QuizzerStatus quizzer_status;
	
	public Long getQuizzer_ID() {
		return quizzer_ID;
	}

	public void setQuizzer_ID(Long quizzer_ID) {
		this.quizzer_ID = quizzer_ID;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Date getLast_Played() {
		return last_Played;
	}

	public void setLast_Played(Date last_Played) {
		this.last_Played = last_Played;
	}

	public Integer getTotal_Played() {
		return total_Played;
	}

	public void setTotal_Played(Integer total_Played) {
		this.total_Played = total_Played;
	}

	public Integer getTotal_Win() {
		return total_Win;
	}

	public void setTotal_Win(Integer total_Win) {
		this.total_Win = total_Win;
	}

	public QuizzerStatus getQuizzer_status() {
		return quizzer_status;
	}

	public void setQuizzer_status(QuizzerStatus quizzer_status) {
		this.quizzer_status = quizzer_status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((employee == null) ? 0 : employee.hashCode());
		result = prime * result + ((quizzer_ID == null) ? 0 : quizzer_ID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Quizzer other = (Quizzer) obj;
		if (employee == null) {
			if (other.employee != null)
				return false;
		} else if (!employee.equals(other.employee))
			return false;
		if (quizzer_ID == null) {
			if (other.quizzer_ID != null)
				return false;
		} else if (!quizzer_ID.equals(other.quizzer_ID))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Quizzer [quizzer_ID=" + quizzer_ID + ", employee=" + employee + ", last_Played=" + last_Played
				+ ", total_Played=" + total_Played + ", total_Win=" + total_Win + ", quizzer_status=" + quizzer_status
				+ "]";
	}
}
