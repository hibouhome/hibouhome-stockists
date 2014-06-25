package com.hibouhome.stockists.xml;

import java.util.Comparator;

import org.apache.commons.lang3.Validate;

/**
 * {@link Comparator} for sorting {@link Sortable} entities.
 * 
 * @author Jonathan Wright
 *
 */
public final class SortableComparator implements Comparator<Sortable> {

	/**
	 * Compares to instances of {@link Sortable}, neither of which can be null
	 * 
	 * @see Comparator
	 */
	@Override
	public int compare(final Sortable s1, final Sortable s2) {
		Validate.notNull(s1, "s1 cannot be null");
		Validate.notNull(s2, "s2 cannot be null");

		if (hasDisplayIndex(s1) && hasDisplayIndex(s2)) {
			// both have a display index, compare indexes
			return s1.getDisplayIndex().compareTo(s2.getDisplayIndex());
		}
		if (hasDisplayIndex(s1) && !hasDisplayIndex(s2)) {
			// only this instance has an index, return -1
			return -1;
		}
		if (!hasDisplayIndex(s1) && hasDisplayIndex(s2)) {
			// only the other instance has a display index, return 1
			return 1;
		}
		// neither has a display index, compare names
		// name will never be null as the attribute is required in stockists.xsd
		return s1.getName().compareTo(s2.getName());
	}

	private boolean hasDisplayIndex(final Sortable sortable) {
		return sortable.getDisplayIndex() != null;
	}
}