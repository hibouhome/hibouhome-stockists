package com.hibouhome.stockists.xml;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URISyntaxException;

import com.hibouhome.stockists.xml.jaxb.Country;
import com.hibouhome.stockists.xml.jaxb.Region;
import com.hibouhome.stockists.xml.jaxb.Stockist;
import com.hibouhome.stockists.xml.jaxb.Stockists;

public abstract class TestUtils {
	
	/**
	 * Creates an object representation of <code>JAXBHelperTest_valid.xml<code>
	 */
	public static Stockists getStockists() {
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
	
	public static File getFile(final String resource) throws URISyntaxException {
		final File file = new File(JAXBHelper.class.getResource(resource).toURI());
		assertTrue(file.isFile());
		return file;
	}
}
