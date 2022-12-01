package uk.ac.aston.oop.javafx.assessed;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import uk.ac.aston.oop.javafx.assessed.model.Item;

public class RemoveController {

	@FXML private Label lblItem;

	private final Item selectedItem;
	private boolean confirmed = false;

	public RemoveController(Item selectedItem) {
		this.selectedItem = selectedItem;
	}

	public boolean isConfirmed() {
		return confirmed;
	}

	@FXML
	public void initialize() {
		lblItem.setText(selectedItem.toString());
	}

	@FXML
	public void cancelPressed() {
		lblItem.getScene().getWindow().hide();
	}

	@FXML
	public void confirmPressed() {
		confirmed = true;
		lblItem.getScene().getWindow().hide();
	}
}
