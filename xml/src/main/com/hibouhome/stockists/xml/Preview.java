package com.hibouhome.stockists.xml;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.Validate;

/**
 * Represents a stockist page preview
 * 
 * @author Jonathan Wright
 *
 */
public final class Preview {

	private static final String TEMPLATE_NAME = "preview.html";
	private static final String TEMPLATE_ENCODING = "UTF-8";
	private static final String TEMPLATE_PLACEHOLDER = "$stockists-html$";
	private static final String previewTemplate;

	static {
		try {
			previewTemplate = IOUtils.toString(Preview.class.getResource(TEMPLATE_NAME), TEMPLATE_ENCODING);
		} catch (IOException e) {
			// unrecoverable error
			throw new ApplicationError("Error reading template preview.html", e);
		}
	}

	private final String html;
	private final String preview;

	/**
	 * Constructs a new instance.
	 * 
	 * @param html the generated stockist HTML
	 * @param preview the preview page HTML
	 */
	public Preview(final String html) {
		Validate.notBlank(html);
		this.html = html;
		preview = previewTemplate.replace(TEMPLATE_PLACEHOLDER, html);
	}

	public String getHTML() {
		return html;
	}

	public String getPreview() {
		return preview;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((html == null) ? 0 : html.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Preview other = (Preview) obj;
		if (html == null) {
			if (other.html != null)
				return false;
		} else if (!html.equals(other.html))
			return false;
		return true;
	}
}