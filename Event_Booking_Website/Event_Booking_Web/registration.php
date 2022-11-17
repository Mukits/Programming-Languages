<!DOCTYPE html>
  <html lang="en">
  	<HEAD>                <!-- that is the head of the website-->
  		<link href="css/registration.css" rel="stylesheet" type="text/css" />         
  		<link href="css/myfonts.css" rel="stylesheet" type="text/css" />
  		<link href="css/index.css" rel="stylesheet" type="text/css" />
      <script type="text/javascript" src=javascript/registration.js> </script>
  		<style>
          div {
                
                background-image: url('images/fi.webp');
                background-size: cover;
                background-repeat: no-repeat;
                      
              }
     </style> 
  		<meta charset="UTF-8">
  		<TITLE>Registration</TITLE>
  	</HEAD>

  	<BODY>           <!-- this is body which contains the structure of the whole webpage-->
  		
  		<div id="container"><!-- that is the div that contains the every parts of the webpage-->
  			<div id="toparea" style="background-image: url('images/li.webp');background-size: cover;">  <!-- div that contains everything on the top-->
          
          <div id="logo" style="background-image: url('images/li.webp');background-size: cover;">   <!-- div for the logo, contains the image source -->
          
          <img src="images/logo.png" alt="logo">           
          <a  target="_blank"> <img src="images/logo.png" alt="logo"> </a>
          </div>
  	    </div>
        <div id="midarea">
  			      <div class="main_div">
      <div class="title">Registration Form</div>
      
      <form name="registrationForm" action="php/reg.php" onsubmit= "return regVal();" method="post">
      	<div class="input_box">
          <input type="text" name="name" placeholder="Name" >
        </div>
        <div class="input_box">
          <input type="text" name="surname" placeholder="Surname" >
        </div>
        <div class="input_box">
          <input type="text" name="email" placeholder="Email" >
        </div>
        <div class="input_box">
          <input type="text" name="uId" placeholder="Student ID" >
        </div>
        <div class="input_box">
          <input type="tel" name="phone" placeholder="Phone" >
        </div>
        <div class="input_box">
          <input type="password" name="pwd" placeholder="Password" >
        </div>
        <div class="input_box">
          <input type="password" name="pwdConfirm" placeholder="Confirm Password" >
        </div>
        <div class="input_box button">
          <input type="submit" name="submit" value="Register">
      </div>
      <div id="alreadyreg"><p>
        Already a user? <a href="index.php">Log in</a>
      </p>
      </div>
      <div id="eresult"> </div>
      </form>
    </div>
    </div>	
 
    <div id="botarea" style="background-image: url('images/li.webp'); background-size: cover;">
        <div id="footer" style="background-image: url('images/li.webp'); background-size: cover;"> <p>Copyright © 2021 Aston University </p>  </div>
    </div>
    </div>
  		</div>
  	</BODY>
  </HTML> 