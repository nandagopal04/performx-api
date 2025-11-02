package com.performx.constant;

public enum AggregateType {

	COUNT("COUNT"), SUM("SUM"), AVG("AVG"), MAX("MAX"), MIN("MIN");

	private String value;

	private AggregateType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
