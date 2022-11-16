import java.util.Scanner;
import java.util.ArrayList;
Scanner in;
private  ArrayList <Contact > contacts;
Contact contact;
int current;
Button buttonRight;
Button buttonLeft;

void setup() {
 size(800,400);
 current = 0;
 buttonRight = new Button(200,200,100,75);
 buttonLeft = new Button(400,200,100,75);
 buttonRight.setLabel("Prev");
 buttonLeft.setLabel("Next");
 contacts = new  ArrayList <Contact >();
 in = InputReader.getScanner("put here your cvs file path"); //insert you csv file path note: use  double \ for windows
 String header;
 header = in.nextLine();
 //println(header);
 
 while (in.hasNext()) {
   
   String csv;
   
   csv = in.nextLine();
   //println(csv);
   String[] data;
   data = csv.split(",");
   contact = new  Contact(data[0], data[1]);
   //println(track);
   contact.setEmail(data[2]);
   contact.setPhone(data[3]);
   contacts.add(contact );
 }
 //println(tracks.size());
}

void draw() {
  background(255);
  Contact c;
  c = contacts.get(current);
  fill(0);
  textSize(20);
  text(c.toString(),200,100);
  buttonRight.display();
  buttonLeft.display();
  
}
void mousePressed() {
  
 
  if (buttonLeft.isInside(mouseX,mouseY)) {
    
    current++;
    
  } if (current >=2) {
    current = 2;
  }
  
  if (buttonRight.isInside(mouseX,mouseY)) {
    current --;
  } if (current <=0) {
    current = 0;
  
  }
  
}
