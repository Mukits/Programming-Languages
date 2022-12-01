package uk.ac.aston.oop.uml.media;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CDTest extends AbstractItemSubclassTest<CD> {

	private static final int CD_TRACKS = 42;
	private static final String CD_ARTIST = "Myself";
	private static final String CD_TITLE = "My Song";
	private static final int CD_LENGTH = 30;

	@Override
	protected String getItemTitle() {
		return CD_TITLE;
	}

	@Override
	protected int getPlayMinutes() {
		return CD_LENGTH;
	}

	@Override
	protected CD createInstance() {
		return new CD(CD_TITLE, CD_ARTIST, CD_TRACKS, CD_LENGTH);
	}

	@Test
	public void artist() {
		assertEquals(CD_ARTIST, item.getArtist(),
			"The CD should know its artist");
		assertTrue(item.toString().contains(CD_ARTIST),
			"The CD should mention the artist in its toString() method");
	}

	@Test
	public void numberOfTracks() {
		assertEquals(CD_TRACKS, item.getNumberOfTracks(),
			"The CD should know its number of tracks");
		assertTrue(item.toString().contains(CD_TRACKS + ""),
			"The CD should mention the number of tracks in its toString() method");
	}

}
