package com.hibouhome.stockists.xml;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.helpers.ValidationEventImpl;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

public class InvalidStockistDataExceptionTest {

	private JAXBException cause;
	private List<ValidationEvent> warnings;
	private List<ValidationEvent> errors;
	private List<ValidationEvent> fatalErrors;
	private List<ValidationEvent> allEvents;

	@Before
	public void setUp() {
		cause = new JAXBException("JAXBException message");
		warnings = buildEventList(ValidationEvent.WARNING, "Warning1", "Warning2");
		errors = buildEventList(ValidationEvent.ERROR, "Error1", "Error2", "Error3");
		fatalErrors = buildEventList(ValidationEvent.FATAL_ERROR, "FatalError1");
		final List<ValidationEvent> temp = new ArrayList<ValidationEvent>();
		temp.addAll(warnings);
		temp.addAll(errors);
		temp.addAll(fatalErrors);
		allEvents = Collections.unmodifiableList(temp);
	}

	@Test(expected = NullPointerException.class)
	public void constructorNullMessage() {
		new InvalidStockistDataException(null, null, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void constructorBlankMessage() {
		new InvalidStockistDataException(" ", null, null);
	}

	@Test(expected = NullPointerException.class)
	public void constructorNullEvents() {
		new InvalidStockistDataException("message", null, cause);
	}

	@Test
	public void getEventTypes() {
		final InvalidStockistDataException e = new InvalidStockistDataException("message", allEvents.toArray(new ValidationEvent[0]), cause);
		assertEquals(warnings, e.getWarnings());
		assertEquals(errors, e.getErrors());
		assertEquals(fatalErrors, e.getFatalErrors());
	}

	@Test
	public void getMessage() {
		final InvalidStockistDataException e = new InvalidStockistDataException("message", allEvents.toArray(new ValidationEvent[0]), cause);
		assertTrue(StringUtils.isNoneBlank(e.getMessage()));
	}

	@Test
	public void getLocalizedMessage() {
		final InvalidStockistDataException e = new InvalidStockistDataException("message", allEvents.toArray(new ValidationEvent[0]), cause);
		assertTrue(StringUtils.isNotBlank(e.getMessage()));
	}

	private static List<ValidationEvent> buildEventList(final int severity, final String... messages) {
		final List<ValidationEvent> events = new ArrayList<ValidationEvent>(messages.length);
		for (final String message : messages) {
			events.add(new ValidationEventImpl(severity, message, null));
		}
		return Collections.unmodifiableList(events);
	}
}
