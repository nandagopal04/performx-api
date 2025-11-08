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
	private Object value; // Single value OR list for IN
	private Object additionalValue; // Used for BETWEEN only

	public boolean isBetween() {
		return operator == FilterOperator.BETWEEN;
	}

	public boolean isCollectionBased() {
		return operator == FilterOperator.IN || operator == FilterOperator.NOT_IN;
	}

}
