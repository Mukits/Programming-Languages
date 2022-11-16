Car[] car; //<>// //<>//
int numCars;
RaceTrack track;
boolean raceOver;
int win = 0;
void setup() {
  size(1000, 600);

  numCars = 10;

  car = new Car[numCars];
  float pos = height/(numCars+1);
  track = new RaceTrack();
  for (int index=0; index < numCars; index++) 
  {
    car[index]= new Car(track.getStartPosition(), (pos*index)+90, 60, color(200, 100, 100));
  }
  raceOver = false;
}

void draw() {
  background(70);
  track.display();


  for (int index=0; index<car.length; index++) {

    car[index].display();
    car[index].getScore();
  }

  if (!raceOver ) {
    for (int i = 0; i <car.length; i++) {
      car[i].move();
    }
    checkWin();
    if (win >0  ) {
      if (win >1) {
        raceOver = true;
        println("draw");
      } else if (win == 1) {

        for (int j=0; j<car.length; j++) {
          if (car[j].getPosition() >= track.getFinishPosition() ) {
            raceOver = true;

            car[j].increaseScore();
          }
        }
      }
      println("press 's' to restart");
    }
  }
}
boolean checkWin() {


    for (int index=0; index<car.length; index++) {

      if (car[index].getPosition() >= track.getFinishPosition() ) {

        win++;
      }

      if (win >1) {
        return false;
      }
    }
    return true;
  }

  void keyPressed() {
    if (key == 's' && raceOver) {
      win = 0;
      float startLine = track.getStartPosition();
      for (int i=0; i<numCars; i++) {
        car[i].setPosition(startLine);
      }

      raceOver = false;
    }
  }
