package uk.ac.aston.oop.uml.media;

public abstract class Item {

	private final String title;
	private final int playMinutes;
	private String comment = "";
	private boolean owned = false;

	public Item(String title, int playMinutes) {
		this.title = title;
		this.playMinutes = playMinutes;
	}

	public int getPlayMinutes() {
		return playMinutes;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getComment() {
		return comment;
	}

	public boolean isOwned() {
		return owned;
	}

	public void setOwned(boolean b) {
		this.owned = b;
	}

	@Override
	public String toString() {
		return String.format("%s%s: %d - %s",
			owned ? "*" : "", title, playMinutes, comment);
	}
}
