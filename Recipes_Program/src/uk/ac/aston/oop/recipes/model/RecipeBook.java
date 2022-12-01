package uk.ac.aston.oop.recipes.model;

import javafx.beans.Observable;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

public class RecipeBook {

	private ListProperty<Recipe> recipes =
		new SimpleListProperty<>(FXCollections.observableArrayList((e) -> {
			return new Observable[] {
				e.nameProperty(),
				e.cookingMinutesProperty()
			};
		}));

	public ListProperty<Recipe> recipesProperty() {
		return recipes;
	}

}
