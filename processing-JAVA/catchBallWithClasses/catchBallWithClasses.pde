// DO NOT EDIT THIS TAB UNTIL THE LAST STEP
// variable to hold the ball object
Ball ball2;
Ball ball;
// variable to hold the catcher object
Catcher catcher;

// DO NOT EDIT THIS TAB UNTIL THE LAST STEP
void setup() {
  size(800, 600);
  rectMode(CENTER);
  // construct the ball, parameters are speed and width
  ball = new Ball(2, 30);
  // construct the catcher, parameters are speed, width and height
  catcher = new Catcher(1, 140, 20);
  ball2 = new Ball(5, 60);
}

// DO NOT EDIT THIS TAB UNTIL THE LAST STEP
void draw() {
  background(0);
  showScore();  // display the score at the top of the screen
  catcher.display();  // call the function to display the catcher
  ball.display(); // call the function to display the ball
  catcher.move();  // call the function to move the catcher
  ball.move(); // call the function to move the ball
  catcher.checkCaught(ball);  // check if the ball is touching the catcher
  ball2.display();
  ball2.move();
  catcher.checkCaught(ball2);
}

// DO NOT EDIT THIS TAB UNTIL THE LAST STEP
void showScore() {
  fill(255);
  textSize(20);
  int theScore = catcher.getScore();
  text("Score: " + theScore, 10, 50);
}

// DO NOT EDIT THIS TAB UNTIL THE LAST STEP
void keyPressed() {
  // keyboard controls are:
  // x move right
  // z move left
  // s stop the movement
  if (key == 'z' || key == 'x' || key == 's') {
    // pass the z, x, or s to the catcher
    catcher.setCatcherControl(key);  
  }
}
