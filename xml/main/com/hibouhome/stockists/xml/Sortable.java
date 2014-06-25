package com.hibouhome.stockists.xml;

/**
 * Contract for sortable entities.
 * 
 * @author Jonathan Wright
 *
 */
public interface Sortable {

	public String getName();

	public void setName(String value);

	public Integer getDisplayIndex();

	public void setDisplayIndex(final Integer value);
}
