<?php

	 session_start(); 
    session_unset(); 
    session_destroy(); 
    

?>
<!DOCTYPE html>
<html lang="en">
	<HEAD>                               <!-- that is the head of the website-->
		<link href="css/logout.css" rel="stylesheet" type="text/css" />
		<link href="css/myfonts.css" rel="stylesheet" type="text/css" />
		<link href="css/index.css" rel="stylesheet" type="text/css" />
		<style>
		div {
		 	    background-image: url('images/fi.webp');
                background-size: cover;
                background-repeat: no-repeat;
		}
		</style> 
		<TITLE>Aston/Homepage</TITLE>
		<meta charset="UTF-8">
	</HEAD>

	<BODY>                               <!-- this is body which contains the structure of the whole webpage-->
		<div id="container">             <!-- that is the div that contains the every parts of the webpage-->
			<div id="toparea" style="background-image: url('images/li.webp');background-size: cover;">  <!-- div that contains everything on the top-->
	          
	          <div id="logo" style="background-image: url('images/li.webp');background-size: cover;">   <!-- div for the logo, contains the image source -->
	          
	          <img src="images/logo.png" alt="logo">           
	          <a  target="_blank"> <img src="images/logo.png" alt="logo"> </a>
	          </div>
	  	    </div>
			<div id="midarea">
			    <div id="textarea"> <!--div containing text paragraph of the page, using class to set font type-->

					<h4> Logged out now! </h4> 
	 				<p>Would like to log in again? <a href="index.php">Log in</a>  </p>
			    </div>
		    </div>
		    <div id="botarea" style="background-image: url('images/li.webp'); background-size: cover;">
        		<div id="footer" style="background-image: url('images/li.webp'); background-size: cover;"> <p>Copyright © 2021 Aston University </p>  </div>
   			</div>
        </div>
		</div>	
	</BODY>
</HTML> 