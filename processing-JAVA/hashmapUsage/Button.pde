class Button {


  private float x;
  private float y;
  private float w;
  private float h;
  private String label;
 
  Style style;
  
  public Button(float xp, float yp, float wd, float ht, Style y) {
    
  this.x= xp;
  this.y = yp;
  this.w = wd;
  this.h = ht;
  this.style = y;
  }

  public void display() {
    fill(this.style.getBackground());
    rect(x, y, w, h);
    fill(this.style.getStrokeColor());
    text(this.style.getName(),x+20,y+40);
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
