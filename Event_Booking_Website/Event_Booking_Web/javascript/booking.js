function bookingVal(){
  var email = document.forms["bookingForm"]["email"].value;
  
  var ckEmail = /[0-9]+@aston.ac.uk/;

  if (email=="") {
    document.getElementById("eresult").innerHTML = "check you have filled the email field";
    return false;
  } 
  
  if (!ckEmail.test(email)) {
    document.getElementById("eresult").innerHTML = "please use a valid aston email address";
    return false;
  }

}