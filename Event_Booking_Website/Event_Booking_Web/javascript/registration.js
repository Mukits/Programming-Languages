  function regVal() {
  var name = document.forms["registrationForm"]["name"].value;
  var surname = document.forms["registrationForm"]["surname"].value;
  var email = document.forms["registrationForm"]["email"].value;
  var StudentID = document.forms["registrationForm"]["uId"].value;
  var phone = document.forms["registrationForm"]["phone"].value;
  var password = document.forms["registrationForm"]["pwd"].value;
  var passwordConf = document.forms["registrationForm"]["pwdConfirm"].value;
  var ckString =  /^[a-zA-Z]+$/;
  var ckEmail = /[0-9]+@aston.ac.uk/;
  var ckInt = /^[0-9]*$/;
  var ckIntLength = /(^\d{10}$)/;
  var strPass = /^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?[0-9])[a-zA-Z0-9]{8,}$/;

  if (name == "" || surname == "" || email== "" || StudentID== "" || phone== "" || password== "" || passwordConf== "") {
    document.getElementById("eresult").innerHTML = "check you have filled all the fields correctly";
    return false;
  } 
  if (!ckString.test(name) || !ckString.test(surname) ) {
    document.getElementById("eresult").innerHTML = "name and surname values cannot contain numbers or special characters";
    return false;
  }
  if (!ckEmail.test(email)) {
    document.getElementById("eresult").innerHTML = "email must be of the following format: e.g 123456789@aston.ac.uk";
    return false;
  }
  if (!ckInt.test(StudentID)){
	document.getElementById("eresult").innerHTML = "StudentID cannot have letters or special characters";
    return false;

  }
  if (!ckInt.test(phone)){
	document.getElementById("eresult").innerHTML = "phone number cannot have letters or special characters";
    return false;

  }
  else if (!ckIntLength.test(phone) ){
	document.getElementById("eresult").innerHTML = "phone number must be in the following format: XXXXXXXXXX e.g 1234567890";
    return false;

  }

  if (password !== passwordConf) {
    document.getElementById("eresult").innerHTML = "passwords don't match";
    return false;
  }
 
  if (!strPass.test(password)) {
    document.getElementById("eresult").innerHTML = "password is weak, please use at least 8 characters with addition of a number, uppercase and lowercase letter";
    return false;
  }

}