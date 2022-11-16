int  numRows;
int  rowLength;
int  startX ;
int  startY ;
int  rectWidth;
int  rectHeight;
boolean isColour;
void  setup() {
  size(900, 400);
  stroke(0) ;
  numRows = 1;
  rowLength = 10;
  startY  = 350;
  rectWidth = 75;
  rectHeight = 30;
  isColour = false ;
}
void  draw() {
  background(255);
  startX  = 50;
  int y = startY;
  for (int rowCount = 0; rowCount <numRows; rowCount++) {
    int counter = rowCount;
    startX += rectWidth/2;
    int x = startX;
    for (int count = 0; count <rowLength-counter; count++) {
      rect(x, y, rectWidth, rectHeight);
      x = x + rectWidth;
    }
    y = y - rectHeight;
  }
}
void  mousePressed() {
  if (numRows<10) { 
    numRows ++;
  } else { 
    numRows = 0;
    isColour = !isColour;

    if (isColour) {
      float r = random(255);
      float g = random(255);
      float b = random(255);

      fill (r, g, b);
    } else {
      fill(255);
    }
  }
}
