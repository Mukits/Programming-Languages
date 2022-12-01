package uk.ac.aston.oop.recipes;

import java.io.File;
import java.util.Optional;
import java.util.function.Function;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;
import uk.ac.aston.oop.recipes.io.RecipeFormat;
import uk.ac.aston.oop.recipes.io.RecipeFormatRegistry;
import uk.ac.aston.oop.recipes.io.exceptions.RecipeLoadingException;
import uk.ac.aston.oop.recipes.io.exceptions.RecipeSavingException;
import uk.ac.aston.oop.recipes.model.Recipe;
import uk.ac.aston.oop.recipes.model.RecipeBook;

public class RecipesController {

	private static interface RecipeFileHandler {
		void handle(RecipeFormat fmt, File resultWithExtension);
	}

	private final RecipeBook book = new RecipeBook();

	@FXML
	private ListView<Recipe> listRecipes;

	@FXML
	private Slider sliderCookMinutes;

	@FXML
	private TextField txtName;

	@FXML
	private TextArea txtIngredients, txtInstructions;

	@FXML
	private Button btnRemove, btnSave;

	@FXML
	public void initialize() {
		listRecipes.setItems(book.recipesProperty());

		ReadOnlyObjectProperty<Recipe> selItemProperty
			= listRecipes.getSelectionModel().selectedItemProperty();

		selItemProperty.addListener(this::selectedItemChanged);
		selectedItemChanged(selItemProperty, null, selItemProperty.get());
	}

	@FXML
	public void addPressed() {
		Recipe recipe = new Recipe();
		listRecipes.itemsProperty().get().add(recipe);
	}

	@FXML
	public void removePressed() {
		if (!listRecipes.getSelectionModel().isEmpty()) {
			int iRecipe = listRecipes.getSelectionModel().getSelectedIndex();
			listRecipes.itemsProperty().get().remove(iRecipe);
		}
	}

	@FXML
	public void loadPressed() {
		FileChooser chooser = createFileChooser();
		chooser.setTitle("Load Recipe from File");
		useRegisteredLoaderOn(chooser, chooser::showOpenDialog, this::doLoad);
	}

	@FXML
	public void savePressed() {
		if (listRecipes.getSelectionModel().isEmpty()) {
			// nothing was selected - do nothing
			return;
		}

		Recipe selectedRecipe = listRecipes.getSelectionModel().getSelectedItem();
		FileChooser chooser = createFileChooser();
		chooser.setTitle("Save Recipe to File");
		useRegisteredLoaderOn(chooser, chooser::showSaveDialog,
			(fmt, resultWithExt) -> { doSave(selectedRecipe, fmt, resultWithExt); });
	}

	protected void doLoad(RecipeFormat fmt, File resultWithExtension) {
		/*
		 * TODO Once you create your custom loading exception, you'll have to catch it
		 * here and show an alert.
		 */
		try {
			Recipe recipe = fmt.load(resultWithExtension);
			book.recipesProperty().add(recipe);
		} catch (RecipeLoadingException ex) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error loading recipe");
			alert.setHeaderText("There was an error loading the recipe:");
			alert.setContentText(ex.getMessage());
			alert.showAndWait();
		}
	}

	protected void doSave(Recipe selectedRecipe, RecipeFormat fmt, File resultWithExt) {
		/*
		 * TODO Once you create your custom saving exception, you'll have to catch it
		 * here.
		 */
		try {
			fmt.save(selectedRecipe, resultWithExt);
		} catch (RecipeSavingException ex) {
			Alert alert = new Alert(AlertType.ERROR, "Error saving recipe");
			alert.setTitle("Error saving recipe");
			alert.setHeaderText("There was an error saving the recipe");
			alert.getDialogPane().setContent(new Label(ex.getMessage()));
			alert.showAndWait();
		}
	}

	private void useRegisteredLoaderOn(FileChooser chooser, Function<Window, File> chooserMethod, RecipeFileHandler c) {
		File result = chooserMethod.apply(txtIngredients.getScene().getWindow());
		if (result == null) {
			// User cancelled chooser
			return;
		}
	
		String selectedExtension = chooser.getSelectedExtensionFilter().getExtensions().get(0);
		result = ensureFileHasExtension(result, selectedExtension);
		if (result == null) {
			// User cancelled overwrite
			return;
		}
	
		Optional<RecipeFormat> loader = RecipeFormatRegistry.getInstance().getLoaderByFile(result);
		if (loader.isPresent()) {
			c.handle(loader.get(), result);
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Unknown extension");
			alert.setContentText("Unknown extension for '" + result.getName() + "'");
			alert.showAndWait();
		}
	}

	private FileChooser createFileChooser() {
		FileChooser chooser = new FileChooser();
		for (RecipeFormat loader : RecipeFormatRegistry.getInstance().getLoaders()) {
			chooser.getExtensionFilters().add(new ExtensionFilter(loader.getDescription(), "*." + loader.getExtension()));
		}
		return chooser;
	}

	private File ensureFileHasExtension(File result, String extension) {
		// Strip out initial *. if present
		if (extension.startsWith("*.")) {
			extension = extension.substring("*.".length());
		}

		if (result != null && !result.getName().endsWith("." + extension)) {
			result = new File(result.getPath() + "." + extension);
			if (result.exists()) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("File exists");
				alert.setHeaderText(String.format("File %s already exists", result.getPath()));
				alert.setContentText("Overwrite?");
				if (alert.showAndWait().get() != ButtonType.OK) {
					result = null;
				}
			}
		}
		return result;
	}

	private void selectedItemChanged(ObservableValue<? extends Recipe> prop, Recipe oldValue, Recipe newValue) {
		// Unbind all controls from the old selection 
		if (oldValue != null) {
			Bindings.unbindBidirectional(txtName.textProperty(), oldValue.nameProperty());
			Bindings.unbindBidirectional(sliderCookMinutes.valueProperty(), oldValue.cookingMinutesProperty());
			Bindings.unbindBidirectional(txtIngredients.textProperty(), oldValue.ingredientsProperty());
			Bindings.unbindBidirectional(txtInstructions.textProperty(), oldValue.instructionsProperty());
		}

		// Disable controls if there is no selection
		sliderCookMinutes.setDisable(newValue == null);
		txtName.setDisable(newValue == null);
		txtIngredients.setDisable(newValue == null);
		txtInstructions.setDisable(newValue == null);
		btnRemove.setDisable(newValue == null);
		btnSave.setDisable(newValue == null);

		// Bind all controls to the new selection
		if (newValue != null) {
			Bindings.bindBidirectional(txtName.textProperty(), newValue.nameProperty());
			Bindings.bindBidirectional(sliderCookMinutes.valueProperty(), newValue.cookingMinutesProperty());
			Bindings.bindBidirectional(txtIngredients.textProperty(), newValue.ingredientsProperty());
			Bindings.bindBidirectional(txtInstructions.textProperty(), newValue.instructionsProperty());
		} else {
			txtName.setText("");
			sliderCookMinutes.setValue(Recipe.DEFAULT_COOK_TIME);
			txtIngredients.setText("");
			txtInstructions.setText("");
		}
	}
}
	
