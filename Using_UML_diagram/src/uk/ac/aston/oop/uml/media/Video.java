package uk.ac.aston.oop.uml.media;

public class Video extends Item {

	private final String director;

	public Video(String videoTitle, String director, int videoLength) {
		super(videoTitle, videoLength);
		this.director = director;
	}

	public String getDirector() {
		return director;
	}

	@Override
	public String toString() {
		return String.format("Director: %s.\n%s",
			getDirector(), super.toString());
	}

}
