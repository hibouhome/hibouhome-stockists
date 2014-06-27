package com.hibouhome.stockists.ui.editor;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import com.hibouhome.stockists.xml.jaxb.Country;
import com.hibouhome.stockists.xml.jaxb.Region;
import com.hibouhome.stockists.xml.jaxb.Stockist;

/**
 * JavaFX controller for the stockists editor
 * 
 * @author Jonathan Wright
 *
 */
public class EditorController implements Initializable {

	// countries
	@FXML
	private Button addCountryButton;
	@FXML
	private ListView<Country> countryListView;
	// regions
	@FXML
	private Button addRegionButton;
	@FXML
	private ListView<Region> regionListView;
	// stockists
	@FXML
	private Button addStockistButton;
	@FXML
	private ListView<Stockist> stockistListView;

	private Model model;

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		stockistListView.setCellFactory(new Callback<ListView<Stockist>, ListCell<Stockist>>() {

			@Override
			public ListCell<Stockist> call(final ListView<Stockist> param) {
				return new StockistCell();
			}
		});
		addCountryListeners();
		addRegionListeners();
		addStockistListeners();
		addStockistButtonListeners();
	}

	/**
	 * @param model
	 */
	public void setModel(final Model model) {
		this.model = model;
		clearSelections();
		bindModel(model);
		addCountryButton.setDisable(false);
	}

	@FXML
	public void deleteCountry() {
		model.deleteCountry();
	}

	@FXML
	public void deleteRegion() {
		model.deleteRegion();
	}

	@FXML
	public void editStockist() {
		showStockistDialog();
	}

	@FXML
	public void deleteStockist() {
		model.deleteStockist();
	}

	private void clearSelections() {
		countryListView.getSelectionModel().clearSelection();
		regionListView.getSelectionModel().clearSelection();
		stockistListView.getSelectionModel().clearSelection();
	}

	/**
	 * Binds controls to the model
	 * 
	 * @param model
	 */
	private void bindModel(final Model model) {
		// remove existing bindings
		countryListView.itemsProperty().unbind();
		regionListView.itemsProperty().unbind();
		stockistListView.itemsProperty().unbind();
		// add new bindings
		// countries
		countryListView.itemsProperty().bind(model.countryListProperty());
		model.selectedCountryProperty().bind(countryListView.getSelectionModel().selectedItemProperty());
		// regions
		regionListView.itemsProperty().bind(model.regionListProperty());
		model.selectedRegionProperty().bind(regionListView.getSelectionModel().selectedItemProperty());
		addRegionButton.disableProperty().bind(model.selectedCountryProperty().isNull());
		// stockists
		stockistListView.itemsProperty().bind(model.stockistListProperty());
		model.selectedStockistProperty().bind(stockistListView.getSelectionModel().selectedItemProperty());
		addStockistButton.disableProperty().bind(model.selectedCountryProperty().isNull());
	}

	/**
	 * Adds listeners to the {@link Country} {@link ListView}
	 */
	private void addCountryListeners() {
		countryListView.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(final MouseEvent evt) {
				regionListView.getSelectionModel().clearSelection();
				stockistListView.getSelectionModel().clearSelection();
				if (evt.getButton().equals(MouseButton.PRIMARY) && evt.getClickCount() == 2) {
				}
			}
		});
	}

	/**
	 * Add listeners to the {@link Region} {@link ListView}
	 */
	private void addRegionListeners() {
		regionListView.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(final MouseEvent evt) {
				stockistListView.getSelectionModel().clearSelection();
				if (evt.getButton().equals(MouseButton.PRIMARY) && evt.getClickCount() == 2) {
				}
			}
		});
	}

	/**
	 * Adds listeners to the {@link Stockist} {@link ListView}
	 */
	private void addStockistListeners() {
		stockistListView.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(final MouseEvent evt) {
				if (evt.getButton().equals(MouseButton.PRIMARY) && evt.getClickCount() == 2) {
					showStockistDialog();
				}
			}
		});
	}

	/**
	 * 
	 */
	private void addStockistButtonListeners() {
		addStockistButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(final ActionEvent evt) {
				showStockistDialog();
			}
		});
	}

	private void showStockistDialog() {

	}
}
