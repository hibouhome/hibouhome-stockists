package com.hibouhome.stockists.ui.editor;

import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import com.hibouhome.stockists.xml.jaxb.Stockist;

/**
 * Custom cell renderer for {@link Stockist} {@link ListView} items
 * 
 * @author Jonathan Wright
 *
 */
public class StockistCell extends ListCell<Stockist> {

	@Override
	protected void updateItem(final Stockist item, final boolean empty) {
		super.updateItem(item, empty);
		setText(null);
		if (empty) {
			setGraphic(null);
		} else {
			final VBox vbox = new VBox();
			addName(vbox, item);
			addAddress(vbox, item);
			addContactInfo(vbox, item.getTelephone(), "T");
			addContactInfo(vbox, item.getFax(), "F");
			addContactInfo(vbox, item.getEmail(), "E");
			addContactInfo(vbox, item.getWebsite(), "W");
			setGraphic(vbox);
		}
	}

	private static void addName(final VBox vbox, final Stockist item) {
		final HBox nameHBox = new HBox();
		nameHBox.getStyleClass().add("stockist-heading");
		nameHBox.setMaxWidth(Double.MAX_VALUE);
		final Label nameLabel = new Label(item.getName());
		nameLabel.getStyleClass().add("stockist-name");
		nameLabel.setMaxWidth(Double.MAX_VALUE);
		HBox.setHgrow(nameLabel, Priority.ALWAYS);
		nameHBox.getChildren().add(nameLabel);
		if (item.getDisplayIndex() != null) {
			final Label displayIndexLabel = new Label(String.valueOf(item.getDisplayIndex()));
			nameHBox.getChildren().add(displayIndexLabel);
		}
		vbox.getChildren().add(nameHBox);
	}

	private static void addAddress(final VBox vbox, final Stockist item) {
		if (!item.getAddressLines().isEmpty()) {
			final StringBuilder sb = new StringBuilder();
			for (final Iterator<String> i = item.getAddressLines().iterator(); i.hasNext();) {
				sb.append(i.next());
				if (i.hasNext()) {
					sb.append(", ");
				}
			}
			final Label addressLabel = new Label(sb.toString());
			addressLabel.getStyleClass().add("stockist-address");
			vbox.getChildren().add(addressLabel);
		}
	}

	private static void addContactInfo(final VBox vbox, final String value, final String prefix) {
		if (StringUtils.isNotBlank(value)) {
			final Label label = new Label(prefix + ": " + value);
			vbox.getChildren().add(label);
		}
	}
}
