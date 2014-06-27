package com.hibouhome.stockists.xml;

import java.io.File;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.util.JAXBSource;
import javax.xml.bind.util.ValidationEventCollector;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.commons.lang3.Validate;
import org.xml.sax.SAXException;

import com.hibouhome.stockists.xml.jaxb.Stockists;

/**
 * Performs JAXB marshaling and unmarshalling
 * <p>
 * The application should only create one instance of this class to avoid the overhead of recreating
 * {@link JAXBContext} and {@link Schema} objects.
 * </p>
 * 
 * @author Jonathan Wright
 *
 */
public final class JAXBHelper {

	static final String CONTEXT_PATH = Stockists.class.getPackage().getName();
	private static final String SCHEMA_NAME = "stockists.xsd";

	private final JAXBContext jaxbContext;
	private final Schema schema;

	/**
	 * Constructs a new instance
	 */
	public JAXBHelper() {
		try {
			jaxbContext = JAXBContext.newInstance(CONTEXT_PATH);
			final SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			final Source schemaSource = new StreamSource(getClass().getResourceAsStream(SCHEMA_NAME));
			schema = schemaFactory.newSchema(schemaSource);
		} catch (final JAXBException e) {
			// unrecoverable error
			throw new ApplicationError("Error instantiating JAXBContext. Context path: " + CONTEXT_PATH, e);
		} catch (final SAXException e) {
			// unrecoverable error
			throw new ApplicationError("Error instantiating Schema " + SCHEMA_NAME, e);
		}
	}

	/**
	 * Unmarshals stockist data from the specified {@link File}
	 * 
	 * @param file the stockists data XML {@link File}
	 * @return
	 * @throws InvalidStockistDataException if an error occurs
	 */
	public Stockists unmarshal(final File file) throws InvalidStockistDataException {
		Validate.notNull(file, "file cannot be null");
		Validate.isTrue(file.isFile(), "file must be a valid file");

		final ValidationEventCollector eventHandler = new ValidationEventCollector();
		try {
			final Source source = new StreamSource(file);
			final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			unmarshaller.setSchema(schema);
			unmarshaller.setEventHandler(eventHandler);
			return (Stockists) unmarshaller.unmarshal(source);
		} catch (final JAXBException e) {
			final String message = "Error reading stockist data from " + file.getAbsolutePath();
			throw new InvalidStockistDataException(message, eventHandler.getEvents(), e);
		}
	}

	/**
	 * Unmarshals stockist data the specified {@link Source}
	 * 
	 * @param source the {@link Source}
	 * @return a new instance of {@link Stockists}
	 * @throws InvalidStockistDataException if an error occurs
	 */
	public Stockists unmarshal(final Source source) throws InvalidStockistDataException {
		Validate.notNull(source, "source cannot be null");

		final ValidationEventCollector eventHandler = new ValidationEventCollector();
		try {
			final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			unmarshaller.setSchema(schema);
			unmarshaller.setEventHandler(eventHandler);
			return (Stockists) unmarshaller.unmarshal(source);
		} catch (final JAXBException e) {
			throw new InvalidStockistDataException("Error reading stockist data", eventHandler.getEvents(), e);
		}
	}

	/**
	 * Marshals the specified {@link Stockists}
	 * 
	 * @param stockists the {@link Stockists} to marshal to XML
	 * @param result output target {@link Result}
	 * @throws InvalidStockistDataException if an error occurs
	 */
	public void marshal(final Stockists stockists, final Result result) throws InvalidStockistDataException {
		Validate.notNull(stockists, "stockists cannot be null");
		Validate.notNull(result, "result cannot be null");

		final ValidationEventCollector eventHandler = new ValidationEventCollector();
		try {
			final Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setSchema(schema);
			marshaller.marshal(stockists, result);
		} catch (final JAXBException e) {
			throw new InvalidStockistDataException("Error writing stockist data", eventHandler.getEvents(), e);
		}
	}

	/**
	 * Marshals the specified {@link Stockists}
	 * 
	 * @param stockists the {@link Stockists} to marshal to XML
	 * @param file the {@link File} to write the XML to
	 * @throws InvalidStockistDataException if an error occurs
	 */
	public void marshal(final Stockists stockists, final File file) throws InvalidStockistDataException {
		Validate.notNull(stockists, "stockists cannot be null");
		Validate.notNull(file, "file cannot be null");
		Validate.isTrue(file.isFile(), "file must be a valid file");

		final ValidationEventCollector eventHandler = new ValidationEventCollector();
		try {
			final Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setSchema(schema);
			marshaller.marshal(stockists, file);
		} catch (final JAXBException e) {
			final String message = "Error writing stockist data to " + file.getAbsolutePath();
			throw new InvalidStockistDataException(message, eventHandler.getEvents(), e);
		}
	}

	/**
	 * @param stockists the {@link Stockists}
	 * @return a new {@link Source} for the specified {@link Stockists}
	 * @throws JAXBException if an error occurs
	 */
	public Source getSource(final Stockists stockists) throws JAXBException {
		Validate.notNull(stockists, "stockists cannot be null");

		return new JAXBSource(jaxbContext, stockists);
	}
}
