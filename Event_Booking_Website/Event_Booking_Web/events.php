	<?php  
	
	require_once 'php/connection.php';
	require_once 'php/function.php';

	?>
	<!DOCTYPE html>
	<html lang="en">
		<HEAD>                   <!-- that is the head of the website-->
			<link href="css/events.css" rel="stylesheet" type="text/css" /> 
			<link href="css/myfonts.css" rel="stylesheet" type="text/css" />
			<link href="css/index.css" rel="stylesheet" type="text/css" />
			<style>
				div {
					  background-image: url('images/fi.webp');
		              background-size: cover;
		              background-repeat: no-repeat;
				    }
			</style>
			<TITLE>Aston/Events</TITLE>
			<meta charset="UTF-8">
		</HEAD>
		<BODY>                             <!-- this is body which contains the structure of the whole webpage-->
			<div id="container">           <!-- that is the div that contains the every parts of the webpage--> 
			
			<div id="toparea" style="background-image: url('images/li.webp');background-size: cover;">             <!-- div that contains everything on the top -->
				<div id="logo" style="background-image: url('images/li.webp');background-size: cover;">      <!-- div for the logo, contains the image source -->
		
			    <img src="images/logo.png" alt="logo">           
				<a  target="_blank"> <img src="images/logo.png" alt="logo"> </a>
				</div>
				<div id="navbar" class="sigifntuse"> <!-- div that contains the links of the navigation bar --> 
					<div id="homepage"><a href="homepage.php">Homepage</a> </div>
					<div id="events"><a href="events.php">Events</a></div>
					<div id="logout"><a href="logout.php">Logout</a></div>
				</div>
			</div>
			<div id=midarea>
			<div id="sportarea">
		       <div id="miniTi"> <h1> <?php 
		        $type="Sport";
		        echo $type;
				?> </h1> </div>
				<?php
				showLink($conn,$type);
				?>
			</div>

			<div id='culturearea'>
		        <div id="miniTi">  <h1> <?php 
		        $type="Culture";
		        echo $type;
				?> </h1> </div>
				<?php
				showLink($conn,$type);
				?>
			</div>

			<div id='otherarea'>
				<div id="miniTi"> <h1> <?php 
		        $type="Other";
		        echo $type;
				?> </h1> </div>
				<?php
				showLink($conn,$type);
				?>
	        </div>
	        </div>
	        <div id="botarea" style="background-image: url('images/li.webp'); background-size: cover;">
        		<div id="footer" style="background-image: url('images/li.webp'); background-size: cover;"> <p>Copyright © 2021 Aston University </p>  </div>
    		</div>
		</BODY>
	</HTML> 