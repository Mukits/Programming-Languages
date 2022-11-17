<!DOCTYPE html>
<html lang="en">
	<head>                 <!-- that is the head of the website-->
		<link href="css/login.css" rel="stylesheet" type="text/css" />
		<link href="css/myfonts.css" rel="stylesheet" type="text/css" />
		<link href="css/index.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src=javascript/login.js> </script>
		<style>
			div {
				  background-image: url('images/fi.webp');
				  background-size: cover;
				  background-repeat: no-repeat;
			    }
		</style>  
		<TITLE>Aston/Login</TITLE>
		<meta charset="UTF-8">
	</head>
	<body>                         <!-- this is body which contains the structure of the whole webpage-->
		<div id="container">          <!-- that is the div that contains the every parts of the webpage-->
			<div id="toparea" style="background-image: url('images/li.webp');background-size: cover;">  <!-- div that contains everything on the top-->
          
	          	<div id="logo" style="background-image: url('images/li.webp');background-size: cover;">   <!-- div for the logo, contains the image source -->
	          
		        	<img src="images/logo.png" alt="logo">           
		          	<a  target="_blank"> <img src="images/logo.png" alt="logo"> </a>
            	</div>
  	    	</div>
		<div id="midarea"> 
	   	    <div class="main_div">
	            <div class="title">Login Form</div>
	            <form name="loginForm" action="php/lg.php" onsubmit= "return logVal();" method="post">
			    <div class="input_box">
				    <input type="text" name="email" placeholder="Email Address" >
			    </div>
	            <div class="input_box">
			        <input type="password" name="password" placeholder="Password"> 
	            </div>
	            <div class="input_box button">
	        		<input type="submit" name="submit" value="Login">
	    		</div>
			    <div id="notreg"><p>
					Not registered yet? <a href="registration.php">Register</a>
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
	</body>
</html> 