package com.hibouhome.stockists.xml;

import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * Performs XSLT transformations
 * <p>
 * The application should only create a single instance of this class to avoid recompiling the
 * stylesheet
 * </p>
 * 
 * @author Jonathan Wright
 *
 */
public final class XSLTHelper {

	private static final String TEMPLATE_NAME = "stockists.xsl";

	private final Templates templates;

	/**
	 * Constructs a new instance
	 */
	public XSLTHelper() {
		final InputStream is = getClass().getResourceAsStream(TEMPLATE_NAME);
		final Source source = new StreamSource(is);
		final TransformerFactory transformerFactory = TransformerFactory.newInstance();
		try {
			templates = transformerFactory.newTemplates(source);
		} catch (final TransformerConfigurationException e) {
			// unrecoverable error
			throw new ApplicationError("Error instantiating templates for " + TEMPLATE_NAME, e);
		}
	}

	/**
	 * Transforms the specified {@link Source}
	 * 
	 * @param source the {@link Source} for the transformation
	 * @return the transformed {@link Source} as a {@link String}
	 * @throws TransformerException if an error occurs
	 */
	public String transform(final Source source) throws TransformerException {
		final Transformer transformer;
		try {
			transformer = templates.newTransformer();
		} catch (final TransformerConfigurationException e) {
			// unrecoverable error
			throw new ApplicationError("Error creating transformer", e);
		}
		final StringWriter writer = new StringWriter();
		final Result result = new StreamResult(writer);
		transformer.transform(source, result);
		return writer.toString();
	}
}
