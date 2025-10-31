package com.performx.dto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

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
public class PayrollDTO {

	private long id;
	private BigDecimal basicPay;
	private BigDecimal bonus;
	private Timestamp createdAt;
	private BigDecimal deductions;
	private BigInteger employeeId;
	private Date generatedDate;
	private String month;
	private BigDecimal netPay;
	private Timestamp updatedAt;

}
