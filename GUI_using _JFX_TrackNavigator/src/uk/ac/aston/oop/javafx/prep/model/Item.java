package uk.ac.aston.oop.javafx.prep.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * The Item class represents a multi-media item. Information about the item is
 * stored and can be retrieved. This class serves as a superclass for more
 * specific itms.
 * 
 * @author Michael Kolling and David J. Barnes
 * @version 2002-05-04
 * @author Ian Nabney
 * @version 19/02/2006
 * @author Antonio Garcia-Dominguez
 * @version 09/03/2018
 */
public abstract class Item {
	private final String title;
	private final int playingTime;
	private BooleanProperty gotIt = new SimpleBooleanProperty(false);
	private StringProperty comment = new SimpleStringProperty("");

	/**
	 * Initialise the fields of the item.
	 */
	public Item(String theTitle, int time) {
		title = theTitle;
		playingTime = time;
	}

	/**
	 * Enter a comment for this item.
	 */
	public void setComment(String comment) {
		this.comment.set(comment);
	}

	/**
	 * Return the comment for this item.
	 */
	public String getComment() {
		return comment.get();
	}

	/**
	 * Return the comment property for this item.
	 */
	public StringProperty commentProperty() {
		return comment;
	}

	/**
	 * Set the flag indicating whether we own this item.
	 */
	public void setOwn(boolean ownIt) {
		gotIt.set(ownIt);
	}

	/**
	 * Return information whether we own a copy of this item.
	 */
	public boolean getOwn() {
		return gotIt.get();
	}

	/**
	 * Return the "own it" property for this item.
	 */
	public BooleanProperty ownProperty() {
		return gotIt;
	}

	/**
	 * Returns the playing time for this item.
	 */
	public int getPlayingTime() {
		return playingTime;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(
			String.format("title: %s (%d mins)", title, playingTime));
		if (gotIt.get()) {
			sb.append("*");
		}
		sb.append("\n");
		sb.append("    " + comment.get() + "\n\n");
		return sb.toString();
	}
}
