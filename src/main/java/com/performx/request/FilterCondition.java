package com.performx.request;

import com.performx.constant.FilterOperator;

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
public class FilterCondition {

	private String field;
	private FilterOperator operator;
	private Object value;

}
