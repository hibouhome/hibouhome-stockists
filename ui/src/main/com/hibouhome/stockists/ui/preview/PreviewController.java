package com.hibouhome.stockists.ui.preview;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * 
 * @author Jonathan Wright
 *
 */
public class PreviewController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private WebView webView;

	@FXML
	void initialize() {
		assert webView != null : "fx:id=\"webView\" was not injected: check your FXML file 'PreviewTabContent.fxml'.";
	}

	public void setPreview(final String html) {
		final WebEngine engine = webView.getEngine();
		engine.loadContent(html);
	}
}
