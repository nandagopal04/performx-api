package com.performx.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * The persistent class for the performance_review database table.
 * 
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode()
@Entity
@Table(name = "performance_review")
@NamedQuery(name = "PerformanceReview.findAll", query = "SELECT p FROM PerformanceReview p")
public class PerformanceReview implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -636483939640965306L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "created_at")
	private Timestamp createdAt;
	@Column(name = "employee_id")
	private BigInteger employeeId;
	@Column(name = "project_id")
	private BigInteger projectId;
	private BigDecimal rating;
	private String remarks;
	@Temporal(TemporalType.DATE)
	@Column(name = "review_date")
	private Date reviewDate;
	@Column(name = "updated_at")
	private Timestamp updatedAt;

}