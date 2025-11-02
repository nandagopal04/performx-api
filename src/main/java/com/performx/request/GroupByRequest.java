package com.performx.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents a request for performing group-by operations combined with
 * aggregate functions.
 *
 * <p>
 * Useful for analytics, dashboards, and summary reports.
 * </p>
 *
 * Example JSON:
 * 
 * <pre>
 * {
 *   "groupByFields": ["department", "role"],
 *   "aggregations": [
 *     {"field": "salary", "function": "SUM"},
 *     {"field": "id", "function": "COUNT"}
 *   ],
 *   "filterRequest": { ... }  // Optional filter
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
public class GroupByRequest {

	/** Fields to group the results by. */
	private List<String> groupByFields;

	/** Aggregate functions to apply on grouped data. */
	private List<AggregateFunction> aggregations;

	/** Optional filters applied before grouping. */
	private FilterRequest filterRequest;

}
