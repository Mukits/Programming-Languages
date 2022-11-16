import java.util.HashMap;

 class StyleCollection {
  
 
  private HashMap <String, Style> styles;

 

  public  StyleCollection(){
  styles = new HashMap <String, Style>();
  }
  
  public void addStyle (Style s){
    styles.put(s.getName(),s);  
  }
  
  public void setDefaultStyle (Style s){
    styles.put("default",s);
  }
  
  public Style getStyle (String key){
    return styles.get(key);
  }
  
  public Style getDefaultStyle(){
   return styles.get("default");
  }
  
}
 
