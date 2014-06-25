package com.hibouhome.stockists.xml;

import static org.junit.Assert.*;
import static com.hibouhome.stockists.xml.TestUtils.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.util.JAXBSource;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import com.hibouhome.stockists.xml.jaxb.Stockists;

public class XSLTHelperTest {

	private JAXBContext jaxbContext;
	private XSLTHelper xsltHelper;

	@Before
	public void setUp() throws Exception {
		jaxbContext = JAXBContext.newInstance(JAXBHelper.CONTEXT_PATH);
		xsltHelper = new XSLTHelper();
	}

	@Test
	public void transform() throws Exception {
		final Stockists stockists = getStockists();
		final JAXBSource source = new JAXBSource(jaxbContext, stockists);
		final String html = xsltHelper.transform(source);
		assertTrue(StringUtils.isNotBlank(html));
	}
}
