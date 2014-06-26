package com.hibouhome.stockists.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class PreviewTest {

	@Test
	public void constructor() {
		final String html = "<div>stockist html here!</div>";
		final Preview preview = new Preview(html);
		assertEquals(html, preview.getHTML());
		assertTrue(StringUtils.isNotBlank(preview.getPreview()));
	}
}
