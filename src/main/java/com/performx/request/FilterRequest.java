package com.performx.request;

import java.util.ArrayList;
import java.util.List;

import com.performx.constant.LogicalOperator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents a dynamic filtering request used for querying data using Criteria
 * API or Specification-based filtering.
 *
 * <p>
 * It allows clients to send multiple conditions, sorting options, global search
 * terms, and logical operators (AND/OR).
 * </p>
 *
 * Example JSON:
 * 
 * <pre>
 * {
 *   "search": "john",
 *   "conditions": [
 *     {"field": "age", "operator": "GREATER_THAN", "value": 25},
 *     {"field": "status", "operator": "EQUALS", "value": "ACTIVE"}
 *   ],
 *   "sortOrders": [
 *     {"field": "createdOn", "direction": "DESC"}
 *   ],
 *   "logicalOperator": "AND"
 * }
 * </pre>
 *
 * @author Nanda Gopal Ikkurthi
 * @version 1.0
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode()
@Builder
public class FilterRequest {

	/** Global search keyword (optional). */
	private String search;

	/** List of individual filter conditions. */
	@Default
	private List<FilterCondition> conditions = new ArrayList<>();;

	/** Logical operator connecting multiple conditions (AND/OR). */
	@Default
	private LogicalOperator logicalOperator = LogicalOperator.AND;

	/** Sorting instructions for the query result. */
	@Default
	private List<SortOrder> sortOrders = new ArrayList<>();

}
