int vX;
int vY;
int red;
int green;
int blue;
void setup() {
  size(600,600);
  
  
}
void draw() {
  vX = mouseX;
  vY = mouseY;
  red = int(random(255));
  green = int(random(255));
  blue = int(random(255));
  background(27,79,200);
 
  
  rectMode(CENTER);
  stroke(0);
  fill(120);
  rect(300,500,600,300);
  stroke(0);
  fill(255);
  rect(100,500,80,20);
  rect(250,500,80,20);
  rect(400,500,80,20);
  rect(550,500,80,20);
  stroke(0);
  fill(red,green,blue);
  rect(vX,vY,150,70);
  rect(vX+65,vY-40,25,10);
  stroke(0);
  fill(20,20,20);
  ellipse(vX+40,vY+40,40,40);
  ellipse(vX-40,vY+40,40,40);
  rect(vX,vY-45,70,40);
  stroke(0);
  fill(60);
  ellipse(vX+40,vY+40,15,15);
  ellipse(vX-40,vY+40,15,15);
  stroke(0);
  fill(255,247,0);
  rect(vX-72,vY-27,5,15);
  stroke(0);
  fill(0);
  rect(vX+65,vY-40,25,10);
  stroke(0);
  fill(255,170,0);
  ellipse(40,45,55,55);

 
}
