package com.hibouhome.stockists.ui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;

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
	}
}
