boolean stop;
String writeStop;
String writeWalk;
void setup() {
  size(400,400);
  stop = true;
  writeStop = "STOP";
  writeWalk = "WALK";
}

void draw() {
  
  if (stop) {
  textAlign(LEFT);
  stroke(0);
  fill(255,0,0);
  rect(width/4,height/4,width/2,height/2);
  stroke(0);
  fill(0);
  textSize(60);
  text(writeStop,width/3,height/2);
  } else {
  textAlign(LEFT);
  stroke(0);
  fill(0,255,0);
  rect(width/4,height/4,width/2,height/2);
  stroke(0);
  fill(0);
  textSize(60);
  text(writeWalk,width/3,height/2);
  
  } 
}
void mousePressed() {
  stop = !stop;
}
