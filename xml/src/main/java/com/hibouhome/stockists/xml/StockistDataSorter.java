package com.hibouhome.stockists.xml;

import java.util.Collections;
import java.util.Comparator;

import com.hibouhome.stockists.xml.jaxb.Country;
import com.hibouhome.stockists.xml.jaxb.Region;
import com.hibouhome.stockists.xml.jaxb.Stockists;

/**
 * Sorts countries, regions and stockists into their correct ordering.
 * 
 * @author Jonathan Wright
 *
 */
public final class StockistDataSorter {

	private final Comparator<Sortable> comparator;

	/**
	 * Constructs a new instance.
	 * 
	 * @param comparator the {@link Comparator} used for sorting countries, regions and stockists
	 */
	public StockistDataSorter(final Comparator<Sortable> comparator) {
		this.comparator = comparator;
	}

	/**
	 * Sorts the countries, regions, and stockists contained within the specified {@link Stockists}
	 * 
	 * @param stockists the {@link Stockists}
	 */
	public void sort(final Stockists stockists) {
		Collections.sort(stockists.getCountries(), comparator);
		for (final Country country : stockists.getCountries()) {
			Collections.sort(country.getRegions(), comparator);
			for (final Region region : country.getRegions()) {
				Collections.sort(region.getStockists(), comparator);
			}
			Collections.sort(country.getStockists(), comparator);
		}
	}
}
