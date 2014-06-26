package com.hibouhome.stockists.ui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;

import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

public class MainController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private MenuItem saveAsFileMenuItem;

	@FXML
	private MenuItem saveFileMenuItem;

	@FXML
	private TabPane tabPane;

	@FXML
	void initialize() {
		assert saveAsFileMenuItem != null : "fx:id=\"saveAsFileMenuItem\" was not injected: check your FXML file 'Main.fxml'.";
		assert saveFileMenuItem != null : "fx:id=\"saveFileMenuItem\" was not injected: check your FXML file 'Main.fxml'.";
		assert tabPane != null : "fx:id=\"tabPane\" was not injected: check your FXML file 'Main.fxml'.";
	}

	@FXML
	void exit(ActionEvent event) {
		final Action result = Dialogs.create().owner(tabPane.getScene().getWindow())
				.message("Exit Hibou Home Stockists Editor?").showConfirm();
		if (Dialog.Actions.YES.equals(result)) {
			Platform.exit();
		}
	}

	@FXML
	void openFile(ActionEvent event) {
	}

	@FXML
	void saveFile(ActionEvent event) {
	}

	@FXML
	void saveFileAs(ActionEvent event) {
	}

	@FXML
	void showAbout(ActionEvent event) {
		Dialogs.create().owner(tabPane.getScene().getWindow()).title("About")
				.message("Hibou Home Stockists Editor v1.0").showInformation();
	}
}
