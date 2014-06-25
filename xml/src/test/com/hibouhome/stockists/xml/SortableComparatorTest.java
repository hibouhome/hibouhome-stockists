package com.hibouhome.stockists.xml;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class SortableComparatorTest {

	private SortableComparator comparator;

	@Before
	public void before() {
		comparator = new SortableComparator();
	}

	@Test
	public void countryBothWithDisplayIndex() {
		final SortableImpl s1 = new SortableImpl(null, 1);
		final SortableImpl s2 = new SortableImpl(null, 2);
		assertTrue(comparator.compare(s1, s2) < 0);
		assertTrue(comparator.compare(s2, s1) > 0);
	}

	@Test
	public void countryOneWithDisplayIndex() {
		final SortableImpl s1 = new SortableImpl(null, 1);
		final SortableImpl s2 = new SortableImpl(null, null);
		assertTrue(comparator.compare(s1, s2) < 0);
		assertTrue(comparator.compare(s2, s1) > 0);
	}

	@Test
	public void countryNeitherWithDisplayIndex() {
		final SortableImpl s1 = new SortableImpl("A", null);
		final SortableImpl s2 = new SortableImpl("B", null);
		assertTrue(comparator.compare(s1, s2) < 0);
		assertTrue(comparator.compare(s2, s1) > 0);
	}

	@Test
	public void countryBothWithSameName() {
		final SortableImpl s1 = new SortableImpl("A", null);
		final SortableImpl s2 = new SortableImpl("A", null);
		assertTrue(comparator.compare(s1, s2) == 0);
		assertTrue(comparator.compare(s2, s1) == 0);
	}

	private static class SortableImpl implements Sortable {

		private final String name;
		private final Integer displayIndex;

		public SortableImpl(final String name, final Integer displayIndex) {
			this.name = name;
			this.displayIndex = displayIndex;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public Integer getDisplayIndex() {
			return displayIndex;
		}

		@Override
		public void setName(final String value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void setDisplayIndex(final Integer value) {
			throw new UnsupportedOperationException();
		}
	}
}
