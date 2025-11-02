package com.performx.constant;

public enum FilterOperator {
	
	EQUALS("EQUALS"),
    NOT_EQUALS("NOT_EQUALS"),
    GREATER_THAN("GREATER_THAN"),
    GREATER_THAN_EQUAL("GREATER_THAN_EQUAL"),
    LESS_THAN("LESS_THAN"),
    LESS_THAN_EQUAL("LESS_THAN_EQUAL"),
    BETWEEN("BETWEEN"),
    LIKE("LIKE"),
    IN("IN"),
    NOT_IN("NOT_IN"),
    IS_NULL("IS_NULL"),
    IS_NOT_NULL("IS_NOT_NULL");
	
	private String value;
	
	private FilterOperator(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}

}
