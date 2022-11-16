import java.util.Scanner;

String path = ""; //insert here the path of you data.csv file note: windows uses  "\\"
Scanner input = InputReader.getScanner(path);
PhoneList phoneA = new PhoneList();
PhoneMap phoneS = new PhoneMap();
void setup () {


  input.nextLine();


  while (input.hasNext()) {

    String line = input.nextLine();
    String[] splitLine = line.split(",");
    Phone newPhone = new Phone (splitLine[0]);

    newPhone.setModel(splitLine[1]);
    newPhone.setAnnounced(splitLine[8]);
    newPhone.setWeight_g(float(splitLine[11]));
    newPhone.setInternal_memory(splitLine[21]);
    newPhone.setApprox_price_EUR(int(splitLine[36]));
    // println(newPhone.toString());
    // println(line);
    phoneA.addPhone(newPhone);
    phoneS.addPhone(newPhone);
  }
 // phoneA.sort();
  // phoneA = phoneA.getCostOver(600);
  //Phone foundPhone = phoneA.findPhone("Huawei", "Mate 9 Porsche Design");
  //phoneA.printPhoneList();

  //println(phoneA.getSize());
  //println(foundPhone.toString());
  
  //Phone temp = phoneS.findPhone("Apple","iPhone 7 Plus");
  //print(temp.toString());
  // println(phoneS.getSize());
  //phoneA = phoneS.getCostOver(0);
  //phoneA.printPhoneList();
 // phoneS.printPhoneMap();
 phoneA.sort();
 phoneA.printPhoneList();
}
