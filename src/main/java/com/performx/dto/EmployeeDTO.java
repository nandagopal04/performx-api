package com.performx.dto;

import java.math.BigDecimal;
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
public class EmployeeDTO {

	private long id;
	private Timestamp createdAt;
	private Date dateOfJoining;
	private String department;
	private String designation;
	private String email;
	private String name;
	private BigDecimal salary;
	private String status;
	private Timestamp updatedAt;

}
