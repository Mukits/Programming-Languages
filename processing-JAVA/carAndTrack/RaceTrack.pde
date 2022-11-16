class RaceTrack {
  private float start;
  private float finish;

  public RaceTrack() {
    start = 50;
    finish = width-70;
  }

  public void display() {
    stroke(255);
    line(start, 0, start, height);
    line(finish, 0, finish, height);
    rectMode(CORNER);
    boolean white = true;
    for (int i = 0; i < height; i++) {
      if (white) {
        fill(255);
      } else {
        fill(0);
      }
      rect(finish, i*10, 10, 10);
      white = !white;
    }
  }

  public float getFinishPosition() {
    return finish;
  }

  public float getStartPosition() {
    return start;
  }
}
