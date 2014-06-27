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

	/**
	 * Gets the directory for the open file dialog
	 * 
	 * @return the directory containing the last file opened by the application. If the application
	 *         hasn't yet opened a file, or the directory referenced by this preference no longer
	 *         exists, the user's home directory will be returned
	 */
	public File getLastDir() {
		final String path = preferences.get(LAST_DIR, System.getProperty("user.home"));
		final File dir = new File(path);
		// check the directory still exists as the user may have moved or deleted it since last
		// time the application was run
		if (dir.isDirectory()) {
			return dir;
		} else {
			// last directory preference has been corrupted, return the user's home directory
			return new File(System.getProperty("user.home"));
		}
	}

	/**
	 * Sets the value of the directory containing the most recently opened file
	 * 
	 * @param file the {@link File}. If this value is null or not a valid directory the preference
	 *            will be set to null
	 */
	public void setLastDir(final File file) {
		if (file != null && file.isDirectory()) {
			preferences.put(LAST_DIR, file.getAbsolutePath());
		} else {
			preferences.put(LAST_DIR, null);
		}
	}

}
