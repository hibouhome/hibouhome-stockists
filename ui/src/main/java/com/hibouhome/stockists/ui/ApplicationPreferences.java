package com.hibouhome.stockists.ui;

import java.io.File;
import java.util.prefs.Preferences;

/**
 * Stores and retrieves application preferences.
 * 
 * @author Jonathan Wright
 *
 */
public class ApplicationPreferences {

	private static final String LAST_DIR = "lastDir";
	private final Preferences preferences;

	public ApplicationPreferences() {
		preferences = Preferences.userNodeForPackage(ApplicationPreferences.class);
	}

	public File getLastDir() {
		final String path = preferences.get(LAST_DIR, System.getProperty("user.home"));
		return new File(path);
	}

	public void setLastDir(final File file) {
		if (file.isDirectory()) {
			preferences.put(LAST_DIR, file.getAbsolutePath());
		} else {
			throw new IllegalArgumentException(file.getAbsolutePath() + " is not a valid directory");
		}
	}

}
