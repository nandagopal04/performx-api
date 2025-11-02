package com.performx.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * GlobalException is a unified exception type used across the application to
 * represent any business or system-level failure.
 *
 * <p>
 * It wraps underlying exceptions with meaningful messages and supports chained
 * exception handling for better debugging.
 * </p>
 *
 * @author CHS
 * @version 1.0
 */
@Slf4j
public class GlobalException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new GlobalException with no detail message.
	 */
	public GlobalException() {
		super();
	}

	/**
	 * Constructs a new GlobalException with the specified detail message.
	 *
	 * @param message The detail message.
	 */
	public GlobalException(String message) {
		super(message);
		log.error("GlobalException: {}", message);
	}

	/**
	 * Constructs a new GlobalException with the specified detail message and cause.
	 *
	 * @param message The detail message.
	 * @param cause   The cause (can be null).
	 */
	public GlobalException(String message, Throwable cause) {
		super(message, cause);
		log.error("GlobalException: {} | Cause: {}", message, cause.toString());
	}

	/**
	 * Constructs a new GlobalException with the specified cause.
	 *
	 * @param cause The cause (can be null).
	 */
	public GlobalException(Throwable cause) {
		super(cause);
	}
}