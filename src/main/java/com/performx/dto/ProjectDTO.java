package com.performx.dto;

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
public class ProjectDTO {

	private long id;
	private String client;
	private Timestamp createdAt;
	private Date endDate;
	private String name;
	private Date startDate;
	private String status;
	private Timestamp updatedAt;

}
