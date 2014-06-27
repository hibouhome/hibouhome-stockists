package com.hibouhome.stockists.xml;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Represents a recoverable application error.
 * 
 * @author Jonathan Wright
 *
 */
public class ApplicationException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public ApplicationException() {
		super();
	}

	/**
	 * Constructs a new application exception with the specified cause.
	 * 
	 * @param cause the cause. (A null value is permitted, and indicates that the cause is
	 *            nonexistent or unknown.)
	 */
	public ApplicationException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs a new application exception with the specified detail message and cause.
	 * 
	 * @param message the detail message.
	 * @param cause the cause. (A null value is permitted, and indicates that the cause is
	 *            nonexistent or unknown.)
	 */
	public ApplicationException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * @return a {@link String} representation of the stack trace associated with this exception
	 */
	public String getStackTraceString() {
		final StringWriter stringWriter = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(stringWriter);
		printStackTrace(printWriter);
		return stringWriter.toString();
	}
}
