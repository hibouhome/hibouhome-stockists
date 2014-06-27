package com.hibouhome.stockists.ui.editor;

/**
 * Represents an update to the editor model
 * 
 * @author Jonathan Wright
 *
 * @param <T> the type of the object being updated
 */
public class Update<T> {

	private final T oldValue;
	private final T newValue;

	/**
	 * Create a new instance
	 * 
	 * @param oldValue the old value
	 * @param newValue the new, updated value, that should replace the old one
	 */
	public Update(final T oldValue, final T newValue) {
		this.oldValue = oldValue;
		this.newValue = newValue;
	}

	public T getOldValue() {
		return oldValue;
	}

	public T getNewValue() {
		return newValue;
	}

	public boolean hasOldValue() {
		return oldValue != null;
	}
}
