import java.util.HashMap;

class PhoneMap {

  HashMap<String, Phone> phones;


  PhoneMap() {

    phones = new HashMap <String, Phone>();
  }

  public void addPhone(Phone e) {

    phones.put(e.getBrand() + e.getModel(), e);
  }

  public int getSize() {
    return phones.size();
  }
  public void printPhoneMap() {


    for (String phoneName : phones.keySet()) {

      System.out.println(phones.get(phoneName));
    }
  }

  public PhoneList getCostOver(int price) {
    PhoneList temp = new PhoneList();

    for (String phoneName : phones.keySet()) {
      Phone tempPhone = phones.get(phoneName);
      
      if (tempPhone.getApprox_price_EUR() > price) {
        temp.addPhone(tempPhone);
      }
    }
    return temp;
  }
  public Phone findPhone (String brand, String model) {
    return phones.get(brand +model);
  }
}
