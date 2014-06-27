package com.hibouhome.stockists.xml;

/**
 * Represents an unrecoverable application error
 * 
 * @author Jonathan Wright
 *
 */
public class ApplicationError extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new application error with the specified detail message and cause.
	 * 
	 * @param message the error message
	 * @param cause the Throwable cause
	 */
	public ApplicationError(final String message, final Throwable cause) {
		super(message, cause);
	}
}
