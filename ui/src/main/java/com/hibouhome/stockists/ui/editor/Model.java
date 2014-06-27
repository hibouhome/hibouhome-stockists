package com.hibouhome.stockists.ui.editor;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.InvalidationListener;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableObjectValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;

import com.hibouhome.stockists.xml.SortableComparator;
import com.hibouhome.stockists.xml.jaxb.Country;
import com.hibouhome.stockists.xml.jaxb.Region;
import com.hibouhome.stockists.xml.jaxb.Stockist;
import com.hibouhome.stockists.xml.jaxb.Stockists;

/**
 * UI model
 * 
 * @author Jonathan Wright
 *
 */
public class Model {

	private final StockistProperty stockists;

	// Editor tab properties
	private final ListProperty<Country> countryList;
	private final ObjectProperty<Country> selectedCountry;
	private final ListProperty<Region> regionList;
	private final ObjectProperty<Region> selectedRegion;
	private final ListProperty<Stockist> stockistList;
	private final ObjectProperty<Stockist> selectedStockist;

	/**
	 * Constructs a new instance using the specified {@link Stockists}
	 * 
	 * @param jaxbStockists the {@link Stockists} this model will be based on
	 */
	public Model(final Stockists jaxbStockists) {
		stockists = new StockistProperty(jaxbStockists);
		// countries
		countryList = new SimpleListProperty<Country>(FXCollections.observableList(jaxbStockists.getCountries()));
		selectedCountry = new SimpleObjectProperty<Country>();
		selectedCountry.addListener(new ChangeListener<Country>() {

			@Override
			public void changed(final ObservableValue<? extends Country> observable, final Country oldValue, final Country newValue) {
				if (newValue == null) {
					regionList.set(FXCollections.emptyObservableList());
					stockistList.set(FXCollections.emptyObservableList());
				} else {
					regionList.set(FXCollections.observableList(newValue.getRegions()));
					stockistList.set(FXCollections.observableList(newValue.getStockists()));
				}
			}
		});
		// regions
		regionList = new SimpleListProperty<Region>(FXCollections.observableArrayList());
		selectedRegion = new SimpleObjectProperty<Region>();
		selectedRegion.addListener(new ChangeListener<Region>() {

			@Override
			public void changed(final ObservableValue<? extends Region> observable, final Region oldValue, final Region newValue) {
				if (newValue == null) {
					if (selectedCountry.getValue() != null) {
						stockistList.set(FXCollections.observableList(selectedCountry.getValue().getStockists()));
					}
				} else {
					stockistList.set(FXCollections.observableList(newValue.getStockists()));
				}
			}
		});
		// stockists
		stockistList = new SimpleListProperty<Stockist>(FXCollections.observableArrayList());
		selectedStockist = new SimpleObjectProperty<Stockist>();
	}

	// country list

	public ListProperty<Country> countryListProperty() {
		return countryList;
	}

	public ObjectProperty<Country> selectedCountryProperty() {
		return selectedCountry;
	}

	public void addCountry(final Country country) {
		countryList.add(country);
		fireStockistsChanged();
	}

	public void updateCountry(final Update<Country> update) {
		if (update.hasOldValue()) {
			countryList.remove(update.getOldValue());
		}
		countryList.add(update.getNewValue());
		countryList.sort(new SortableComparator());
		fireStockistsChanged();
	}

	public void deleteCountry() {
		countryList.remove(selectedCountry.getValue());
		fireStockistsChanged();
	}

	// region list

	public ListProperty<Region> regionListProperty() {
		return regionList;
	}

	public ObjectProperty<Region> selectedRegionProperty() {
		return selectedRegion;
	}

	public void addRegion(final Region region) {
		regionList.add(region);
		fireStockistsChanged();
	}

	public void updateRegion(final Update<Region> update) {
		if (update.hasOldValue()) {
			regionList.remove(update.getOldValue());
		}
		regionList.add(update.getNewValue());
		regionList.sort(new SortableComparator());
		fireStockistsChanged();
	}

	public void deleteRegion() {
		regionList.remove(selectedRegion.getValue());
		fireStockistsChanged();
	}

	// stockist list

	public ListProperty<Stockist> stockistListProperty() {
		return stockistList;
	}

	public ObjectProperty<Stockist> selectedStockistProperty() {
		return selectedStockist;
	}

	public void updateStockist(final Update<Stockist> update) {
		if (update.hasOldValue()) {
			stockistList.remove(update.getOldValue());
		}
		stockistList.add(update.getNewValue());
		stockistList.sort(new SortableComparator());
		fireStockistsChanged();
	}

	public void deleteStockist() {
		stockistList.remove(selectedStockist.getValue());
		fireStockistsChanged();
	}

	// other properties

	public ObservableObjectValue<Stockists> stockistsProperty() {
		return stockists;
	}

	private void fireStockistsChanged() {
		stockists.fireValueChangedEvent();
	}

	/**
	 * {@link ObservableObjectValue} implementation that allows for manual firing of value changed
	 * events
	 */
	private static class StockistProperty implements ObservableObjectValue<Stockists> {

		private final Stockists stockists;
		private final List<ChangeListener<? super Stockists>> changeListeners;

		public StockistProperty(final Stockists stockists) {
			this.stockists = stockists;
			changeListeners = new ArrayList<ChangeListener<? super Stockists>>();
		}

		public void fireValueChangedEvent() {
			for (final ChangeListener<? super Stockists> listener : changeListeners) {
				listener.changed(this, stockists, stockists);
			}
		}

		@Override
		public void addListener(final ChangeListener<? super Stockists> listener) {
			changeListeners.add(listener);
		}

		@Override
		public void removeListener(final ChangeListener<? super Stockists> listener) {
			changeListeners.remove(listener);
		}

		@Override
		public Stockists getValue() {
			return stockists;
		}

		@Override
		public void addListener(final InvalidationListener listener) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void removeListener(final InvalidationListener listener) {
			throw new UnsupportedOperationException();
		}

		@Override
		public Stockists get() {
			return stockists;
		}
	}
}
