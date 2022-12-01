package uk.ac.aston.oop.javafx.prep.model;

/**
 * The CD class represents a CD object. Information about the CD is stored and
 * can be retrieved.
 * 
 * @author Michael Kolling and David J. Barnes
 * @version 2002-05-04
 * @author Antonio Garcia-Dominguez
 * @version 09/03/2018
 */
public class CD extends Item {
	private final String artist;
	private final int numberOfTracks;

	/**
	 * Constructor for objects of class CD
	 */
	public CD(String theTitle, String theArtist, int tracks, int time) {
		super(theTitle, time);
		artist = theArtist;
		numberOfTracks = tracks;
	}

	/**
	 * Return the artist for this CD.
	 */
	public String getArtist() {
		return artist;
	}

	/**
	 * Return the number of tracks on this CD.
	 */
	public int getNumberOfTracks() {
		return numberOfTracks;
	}

	@Override
	public String toString() {
		return String.format(
			"Artist: %s. Number of tracks: %d\n%s",
			artist, numberOfTracks, super.toString());
	}
}
