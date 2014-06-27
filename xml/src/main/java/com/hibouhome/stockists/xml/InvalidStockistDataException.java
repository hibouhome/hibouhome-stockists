package com.hibouhome.stockists.xml;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.xml.bind.JAXBException;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.ValidationEvent;

import org.apache.commons.lang3.Validate;

/**
 * 
 * @author Jonathan Wright
 *
 */
public class InvalidStockistDataException extends ApplicationException {

	private static final long serialVersionUID = 1L;

	private final String message;
	private final List<ValidationEvent> warnings;
	private final List<ValidationEvent> errors;
	private final List<ValidationEvent> fatalErrors;

	/**
	 * Constructs a new instance
	 * 
	 * @param message
	 * @param validationEvents
	 * @param cause
	 */
	public InvalidStockistDataException(final String message, final ValidationEvent[] validationEvents, final JAXBException cause) {
		super(cause);
		Validate.notBlank(message, "message cannot be blank");
		Validate.notNull(validationEvents, "validationEvents cannot be null");

		final List<ValidationEvent> events = Arrays.asList(validationEvents);
		warnings = filter(events, ValidationEvent.WARNING);
		errors = filter(events, ValidationEvent.ERROR);
		fatalErrors = filter(events, ValidationEvent.FATAL_ERROR);
		this.message = buildMessage(message);
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public String getLocalizedMessage() {
		return message;
	}

	/**
	 * @return the {@link List} of warning {@link ValidationEvent} associated with this exception
	 */
	public List<ValidationEvent> getWarnings() {
		return warnings;
	}

	/**
	 * @return the {@link List} of error {@link ValidationEvent} warnings associated with this
	 *         exception
	 */
	public List<ValidationEvent> getErrors() {
		return errors;
	}

	/**
	 * @return the {@link List} of fatal error {@link ValidationEvent} associated with this
	 *         exception
	 */
	public List<ValidationEvent> getFatalErrors() {
		return fatalErrors;
	}

	private String buildMessage(final String message) {
		final StringBuilder sb = new StringBuilder(message).append(".");
		append(sb, "warning", warnings);
		append(sb, "error", errors);
		append(sb, "fatal error", fatalErrors);
		return sb.toString();
	}

	private void append(final StringBuilder sb, final String severity, final List<ValidationEvent> events) {
		if (!events.isEmpty()) {
			sb.append(" ");
		}
		for (final Iterator<ValidationEvent> i = events.iterator(); i.hasNext();) {
			final ValidationEvent event = i.next();
			sb.append(severity).append(": ").append(event.getMessage());
			if (getCause() instanceof UnmarshalException && event.getLocator() != null) {
				sb.append(" line: ").append(event.getLocator().getLineNumber());
				sb.append(", column: ").append(event.getLocator().getColumnNumber());
			}
		}
	}

	private static List<ValidationEvent> filter(final List<ValidationEvent> events, final int severity) {
		final Predicate<ValidationEvent> predicate = (final ValidationEvent evt) -> evt.getSeverity() == severity;
		final Stream<ValidationEvent> filtered = events.stream().filter(predicate);
		return Collections.unmodifiableList(filtered.collect(Collectors.toList()));
	}
}
