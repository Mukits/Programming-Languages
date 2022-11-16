String text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras sed mi in purus viverra sodales vel id mi. Donec neque.";
String [] styleNames = {"dark", "light", "red", "blue"};
StyleCollection style;
Style currentStyle;
Button[] buttons;


void setupStyles() {

  style = new StyleCollection(); 


  style.addStyle(new Style ("dark", #8E8E8E, 0, 255, 12));
  style.addStyle(new Style ("light",  #FFFFFF, 0, 0,12));
  style.addStyle(new Style ("red",  #EA4E53, 255, 255, 12));
  style.addStyle(new Style ("blue", #5A69E5, 255, 255,12));

  Style  defaultStyle = new Style("dark", #8E8E8E, 0, 255, 12);

  style.setDefaultStyle(defaultStyle);
  currentStyle = defaultStyle;
}

void setupButtons() {
  buttons = new Button[styleNames.length];
  for (int i= 0; i <styleNames.length;i++) {
    Style temp = style.getStyle(styleNames[i]);
    buttons[i] =  new Button(width/2 -225+(i*110), height/2, 90,70,temp);
  }
  
}

void setup() {
  setupStyles();
  setupButtons();
  size (800, 600);
  textAlign(CENTER);
}


void draw() {
  background (currentStyle.getBackground());
  fill (currentStyle.getFillColor());
  stroke (currentStyle.getStrokeColor());

  text (text, width/2, height/2 - 100);
  
  for (Button button : buttons) {
    
    
    
    button.display();
  }
}
  void mousePressed() {
    
    for (Button button : buttons) {
      if (button.isInside(mouseX,mouseY) )
        
        currentStyle = button.style;
      }
      
    }
    
  
