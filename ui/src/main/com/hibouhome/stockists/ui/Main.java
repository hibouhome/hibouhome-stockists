package com.hibouhome.stockists.ui;

import org.controlsfx.dialog.Dialogs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * 
 * @author Jonathan Wright
 *
 */
public class Main extends Application {

	@Override
	public void start(final Stage primaryStage) {
		try {
			final FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
			final Parent root = loader.load();
			final Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setMaximized(true);
			primaryStage.setTitle("Hibou Home Stockists Editor");
			Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {

				@Override
				public void uncaughtException(final Thread t, final Throwable e) {
					Dialogs.create().owner(primaryStage).title("Error").showException(e);
				}
			});
			primaryStage.show();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(final String[] args) {
		launch(args);
	}
}
