package com.hibouhome.stockists.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URISyntaxException;

import javax.xml.bind.JAXBException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.hibouhome.stockists.xml.jaxb.Country;
import com.hibouhome.stockists.xml.jaxb.Region;
import com.hibouhome.stockists.xml.jaxb.Stockist;
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

	@Test(expected = JAXBException.class)
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

	@Test(expected = JAXBException.class)
	public void marshalInvalid() throws Exception {
		final Stockists stockists = getStockists();
		stockists.getCountries().get(0).setName(null);
		final File file = folder.newFile();
		jaxbHelper.marshal(stockists, file);
	}

	private static File getFile(final String resource) throws URISyntaxException {
		final File file = new File(JAXBHelper.class.getResource(resource).toURI());
		assertTrue(file.isFile());
		return file;
	}

	/**
	 * Creates an object representation of <code>JAXBHelperTest_valid.xml<code>
	 */
	private static Stockists getStockists() {
		// stockist
		final Stockist stockist = new Stockist();
		stockist.setName("Stockist1");
		stockist.setDisplayIndex(2);
		stockist.getAddressLines().add("address line 1");
		stockist.getAddressLines().add("address line 2");
		stockist.getAddressLines().add("address line 3");
		stockist.setTelephone("01 000 000");
		stockist.setFax("02 000 000");
		stockist.setEmail("foo@bar.com");
		stockist.setWebsite("www.foobar.com");
		// region
		final Region region = new Region();
		region.setName("Region1");
		region.getStockists().add(stockist);
		// country
		final Country country = new Country();
		country.setName("Country1");
		country.setDisplayIndex(1);
		country.getRegions().add(region);
		final Stockists stockists = new Stockists();
		stockists.getCountries().add(country);
		return stockists;
	}
}
