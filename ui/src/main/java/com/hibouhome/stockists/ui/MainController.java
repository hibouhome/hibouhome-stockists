package com.hibouhome.stockists.ui;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;

import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

import com.hibouhome.stockists.ui.code.CodeController;
import com.hibouhome.stockists.ui.editor.EditorController;
import com.hibouhome.stockists.ui.preview.PreviewController;
import com.hibouhome.stockists.xml.InvalidStockistDataException;
import com.hibouhome.stockists.xml.JAXBHelper;
import com.hibouhome.stockists.xml.Preview;
import com.hibouhome.stockists.xml.SortableComparator;
import com.hibouhome.stockists.xml.StockistDataSorter;
import com.hibouhome.stockists.xml.XSLTHelper;
import com.hibouhome.stockists.xml.jaxb.Stockists;

/**
 * Top level JavaFX controller
 * 
 * @author Jonathan Wright
 *
 */
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
	private EditorController editorController;
	@FXML
	private PreviewController previewController;
	@FXML
	private CodeController codeController;

	private ApplicationPreferences applicationPreferences;
	private JAXBHelper jaxbHelper;
	private XSLTHelper xsltHelper;
	private StockistDataSorter stockistDataSorter;

	private File openFile;
	private Stockists stockists;

	@FXML
	void initialize() {
		assert saveAsFileMenuItem != null : "fx:id=\"saveAsFileMenuItem\" was not injected: check your FXML file 'Main.fxml'.";
		assert saveFileMenuItem != null : "fx:id=\"saveFileMenuItem\" was not injected: check your FXML file 'Main.fxml'.";
		assert tabPane != null : "fx:id=\"tabPane\" was not injected: check your FXML file 'Main.fxml'.";

		applicationPreferences = new ApplicationPreferences();
		jaxbHelper = new JAXBHelper();
		xsltHelper = new XSLTHelper();
		stockistDataSorter = new StockistDataSorter(new SortableComparator());
	}

	@FXML
	void exit(final ActionEvent event) {
		final Action result = Dialogs.create().owner(tabPane.getScene().getWindow()).message("Exit Hibou Home Stockists Editor?")
				.showConfirm();
		if (Dialog.Actions.YES.equals(result)) {
			Platform.exit();
		}
	}

	@FXML
	void openFile(final ActionEvent event) {
		final FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open");
		fileChooser.setInitialDirectory(applicationPreferences.getLastDir());
		fileChooser.getExtensionFilters().add(new ExtensionFilter("XML files", "*.xml"));
		final File file = fileChooser.showOpenDialog(getWindow());
		applicationPreferences.setLastDir(file.getParentFile());
		try {
			stockists = jaxbHelper.unmarshal(file);
			stockistDataSorter.sort(stockists);
			final String html = xsltHelper.transform(jaxbHelper.getSource(stockists));
			final Preview preview = new Preview(html);
			previewController.setPreview(preview.getPreview());
			codeController.setCode(html);
			saveFileMenuItem.setDisable(false);
			saveAsFileMenuItem.setDisable(false);
			openFile = file;
		} catch (final Exception e) {
			Dialogs.create().owner(getWindow()).title("Error opening " + file.getAbsolutePath()).showException(e);
		}
	}

	@FXML
	void saveFile(final ActionEvent event) {
		saveStockists(openFile);
	}

	@FXML
	void saveFileAs(final ActionEvent event) {
		final FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save As...");
		fileChooser.setInitialDirectory(applicationPreferences.getLastDir());
		fileChooser.getExtensionFilters().add(new ExtensionFilter("XML files", "*.xml"));
		final File file = fileChooser.showSaveDialog(getWindow());
		saveStockists(file);
	}

	@FXML
	void showAbout(final ActionEvent event) {
		Dialogs.create().owner(getWindow()).title("About").message("Hibou Home Stockists Editor v1.0").showInformation();
	}

	private Window getWindow() {
		return tabPane.getScene().getWindow();
	}

	private void saveStockists(final File file) {
		try {
			stockistDataSorter.sort(stockists);
			jaxbHelper.marshal(stockists, file);
		} catch (final InvalidStockistDataException e) {
			Dialogs.create().owner(getWindow()).title("Error saving " + file.getAbsolutePath()).showException(e);
		}
	}
}
