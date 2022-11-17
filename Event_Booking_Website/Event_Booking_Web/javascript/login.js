function logVal() {
  var email = document.forms["loginForm"]["email"].value;
  var password = document.forms["loginForm"]["password"].value;
  

  if (email=="" || password== "") {
    document.getElementById("eresult").innerHTML = "check you have filled all the fields correctly";
    return false;
  } 
  
  

}