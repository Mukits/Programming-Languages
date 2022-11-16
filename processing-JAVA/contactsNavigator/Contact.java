class  Contact {

  private String firstName;
  private String lastName;
  private String emailAddress;
  private String phoneNumber;

  public  Contact(String fname, String lname) 
  {
    firstName = fname;
    lastName = lname;
    
  }
  
 public  void  setEmail(String e) {

    emailAddress = e;
  }
  public  void  setPhone(String pNumber) {

    phoneNumber = pNumber;
  }




  public String toString() {

    return "First Name: "+ firstName + "\nLast Name: " + lastName + "\nEmail Address: "+ emailAddress +"\nPhone Number: " +phoneNumber ;
  }
}
