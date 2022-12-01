package uk.ac.aston.oop.uml.media;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class VideoTest extends AbstractItemSubclassTest<Video> {

	private static final String VIDEO_DIRECTOR = "Stephen Spergbiel";
	private static final String VIDEO_TITLE = "The Exciting Adventures of Java";
	private static final int VIDEO_LENGTH = 9001;

	@Override
	protected String getItemTitle() {
		return VIDEO_TITLE;
	}

	@Override
	protected int getPlayMinutes() {
		return VIDEO_LENGTH;
	}

	@Override
	protected Video createInstance() {
		return new Video(VIDEO_TITLE, VIDEO_DIRECTOR, VIDEO_LENGTH) ;
	}

	@Test
	public void director() {
		assertEquals(VIDEO_DIRECTOR,
			item.getDirector(), "The Video should know its director");
		assertTrue(item.toString().contains(VIDEO_DIRECTOR),
			"The value returned by toString() should include the director");
	}

}
