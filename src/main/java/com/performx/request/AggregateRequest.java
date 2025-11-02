package com.performx.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents a request for performing aggregation operations such as COUNT,
 * SUM, AVG, MAX, or MIN on one or more fields.
 *
 * <p>
 * Supports combining multiple aggregate functions in a single query.
 * </p>
 *
 * Example JSON:
 * 
 * <pre>
 * {
 *   "aggregations": [
 *     {"field": "salary", "function": "SUM"},
 *     {"field": "age", "function": "AVG"}
 *   ],
 *   "filterRequest": { ... }  // Optional filter for conditional aggregation
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
public class AggregateRequest {

	/** List of aggregate function configurations. */
	private List<AggregateFunction> aggregations;

	/** Optional filters to limit the aggregation scope. */
	private FilterRequest filterRequest;

}
