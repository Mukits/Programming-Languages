package uk.ac.aston.oop.javafx.prep.model;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

/**
 * The database class provides a facility to store CD and video 
 * objects. A list of all CDs and videos can be printed to the
 * terminal.
 * 
 * This version does not save the data to disk, and it does not
 * provide any search functions.
 * 
 * @author Michael Kolling and David J. Barnes
 * @version 2002-05-04
 * @author Ian Nabney
 * @version 19/02/2006
 * @author Antonio Garcia-Dominguez
 * @version 09/03/2018
 */
public class Database
{
    protected ListProperty<Item> items;

    /**
     * Construct an empty Database.
     */
    public Database()
    {
        items = new SimpleListProperty<Item>(FXCollections.observableArrayList());
    }

    /**
     * Add an item to the database.
     */
    public void addItem(Item theItem)
    {
        items.add(theItem);
    }

    /**
     * Returns the observable list of all the items in the database.
     */
    public ListProperty<Item> itemsProperty() {
    	return items;
    }

    /**
     * Return a string of all items stored in the database.
     */
    @Override
    public String toString() {
    	StringBuilder sb = new StringBuilder("DOME\n\n");
    	for (Item i : items)
    		sb.append(i.toString());
    	return sb.toString();
    }
}
