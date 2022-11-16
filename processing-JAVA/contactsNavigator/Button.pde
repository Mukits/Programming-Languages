class Button {


  private float x;
  private float y;
  private float w;
  private float h;
  private String label;

  public Button(float xp, float yp, float wd, float ht) {
    
  x= xp;
  y = yp;
  w = wd;
  h = ht;
  
  }

  public void display() {
    fill(255, 0, 0);
    rect(x, y, w, h);
    fill(0);
    text(label,x+10,y+40);
  }
  
  public void setLabel(String label1) {
    
  label = label1;
  }
  
 public boolean isInside(float x1,float y1) {
    
   if (mouseX >= x && mouseX <= x+100 && 
      mouseY >= y && mouseY <= y+75) {
      
      return true;
    } else {
      return false;
    }
    
  }
}
