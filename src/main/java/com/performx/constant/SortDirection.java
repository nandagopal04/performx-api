package com.performx.constant;

public enum SortDirection {

	ASC("ASC"), DESC("DESC");

	private String value;

	private SortDirection(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
