package com.hibouhome.stockists.xml;

import static com.hibouhome.stockists.xml.TestUtils.getFile;
import static com.hibouhome.stockists.xml.TestUtils.getStockists;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.hibouhome.stockists.xml.jaxb.Stockists;

public class JAXBHelperTest {

	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	private JAXBHelper jaxbHelper;

	@Before
	public void setUp() throws Exception {
		jaxbHelper = new JAXBHelper();
	}

	@Test
	public void unmarshalValid() throws Exception {
		final File file = getFile("JAXBHelperTest_valid.xml");
		final Stockists stockists = jaxbHelper.unmarshal(file);
		assertNotNull(stockists);
		assertEquals(getStockists(), stockists);
	}

	@Test(expected = InvalidStockistDataException.class)
	public void unmarshalInvalid() throws Exception {
		final File file = getFile("JAXBHelperTest_invalid.xml");
		jaxbHelper.unmarshal(file);
	}

	@Test
	public void marshalValid() throws Exception {
		final Stockists stockists = getStockists();
		final File file = folder.newFile();
		jaxbHelper.marshal(stockists, file);
		assertTrue(file.isFile());
	}

	@Test(expected = InvalidStockistDataException.class)
	public void marshalInvalid() throws Exception {
		final Stockists stockists = getStockists();
		// set required attribute to null to make the object invalid
		stockists.getCountries().get(0).setName(null);
		final File file = folder.newFile();
		jaxbHelper.marshal(stockists, file);
	}
}
