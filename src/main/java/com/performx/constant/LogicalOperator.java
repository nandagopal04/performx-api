package com.performx.constant;

public enum LogicalOperator {

	AND("AND"), OR("OR");

	private String value;

	private LogicalOperator(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
