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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * The persistent class for the payroll database table.
 * 
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode()
@Entity
@NamedQuery(name = "Payroll.findAll", query = "SELECT p FROM Payroll p")
public class Payroll implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3659895786324843918L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "basic_pay")
	private BigDecimal basicPay;
	private BigDecimal bonus;
	@Column(name = "created_at")
	private Timestamp createdAt;
	private BigDecimal deductions;
	@Column(name = "employee_id")
	private BigInteger employeeId;
	@Temporal(TemporalType.DATE)
	@Column(name = "generated_date")
	private Date generatedDate;
	private String month;
	@Column(name = "net_pay")
	private BigDecimal netPay;
	@Column(name = "updated_at")
	private Timestamp updatedAt;

}