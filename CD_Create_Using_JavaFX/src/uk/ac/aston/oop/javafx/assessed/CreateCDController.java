package uk.ac.aston.oop.javafx.assessed;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import uk.ac.aston.oop.javafx.assessed.model.CD;
import uk.ac.aston.oop.javafx.assessed.model.Database;

public class CreateCDController {

	@FXML
	TextField txtTitle;
	@FXML
	TextField txtArtist;
	@FXML
	CheckBox checkOwn;
	@FXML
	Label labelPlaying;
	@FXML
	Slider sliderPlaying;
	@FXML
	Label labelTracks;
	@FXML
	Slider sliderTracks;

	private Database model;

	public CreateCDController(Database model) {
		this.model = model;
	}

	@FXML
	public void initialize() {
		sliderPlaying.valueProperty().addListener((prop, oldValue, newValue) -> {
			labelPlaying.setText(String.format("Playing time: %.2f", newValue));
		});
		sliderTracks.valueProperty().addListener((prop, oldValue, newValue) -> {
			final int newTracks = ((Double) newValue).intValue();
			labelTracks.setText(String.format("Number of tracks: %d", newTracks));
		});

		sliderTracks.setValue(5);
		sliderPlaying.setValue(5);
	}

	@FXML
	public void createPressed() {
		CD cd = new CD(txtTitle.getText(), txtArtist.getText(),
			(int) sliderTracks.getValue(), (int) sliderPlaying.getValue());
		cd.setOwn(checkOwn.isSelected());
		model.addItem(cd);
		
		txtTitle.getScene().getWindow().hide();
	}

	@FXML
	public void cancelPressed() {
		txtTitle.getScene().getWindow().hide();
	}
}