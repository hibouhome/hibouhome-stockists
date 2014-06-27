package com.hibouhome.stockists.ui.code;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

/**
 * 
 * @author Jonathan Wright
 *
 */
public class CodeController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextArea textArea;

	@FXML
	void initialize() {
		assert textArea != null : "fx:id=\"textArea\" was not injected: check your FXML file 'Code.fxml'.";
	}

	@FXML
	void copyToClipboard(final ActionEvent event) {
		final ClipboardContent cc = new ClipboardContent();
		cc.putString(textArea.getText());
		Clipboard.getSystemClipboard().setContent(cc);
	}

	public void setCode(final String html) {
		textArea.setText(html);
	}
}
