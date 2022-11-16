class Style {
  private int fillColor;
  private int strokeColor;
  private int bgColor;
  private int textSize;
  private String name;
 
  public  Style (String label, int bgCol, int stroke, int fill, int tSize ){
    fillColor= fill;
    strokeColor = stroke;
    bgColor = bgCol;
    textSize = tSize;
    name = label;
  }
   
    public String getName(){
      return name;
    }
   
    public int getFillColor(){
      return fillColor;
    }
   
    public int getStrokeColor(){
      return strokeColor;
    }
   
    public int getTextSize(){
      return textSize;
    }
   
    public int getBackground(){
      return bgColor;
    }
   
  }
