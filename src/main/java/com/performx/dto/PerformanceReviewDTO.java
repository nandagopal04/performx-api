package com.performx.dto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode()
public class PerformanceReviewDTO {

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
