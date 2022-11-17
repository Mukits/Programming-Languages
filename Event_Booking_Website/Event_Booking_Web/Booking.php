<!DOCTYPE html>
<html lang="en">
	<head>              <!-- that is the head of the website-->
		<link href="css/Booking.css" rel="stylesheet" type="text/css" />
		<link href="css/myfonts.css" rel="stylesheet" type="text/css" />
		<link href="css/index.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src=javascript/booking.js> </script>
		 <style>
div {
  background-image: url('images/fi.webp');
  background-size: cover;
  background-repeat: no-repeat;
}
</style> 
		<TITLE>Aston/Booking</TITLE>
		<meta charset="UTF-8">
	</head>

	<body>                    <!-- this is body which contains the structure of the whole webpage-->
		<div id="container">  <!-- that is the div that contains the every parts of the webpage-->
	
		<div id="toparea" style="background-image: url('images/li.webp');background-size: cover;">    
		
		<div id="logo" style="background-image: url('images/li.webp');background-size: cover;">      <!-- div for the logo, contains the image source -->
			<img src="images/logo.png" alt="logo">           
		    <a  target="_blank"> <img src="images/logo.png" alt="logo"> </a>
		</div>
        <div id="navbar" class="sigifntuse">    <!-- div that contains the links of the navigation bar --> 
			<div id="homepage"><a href="homepage.php">Homepage</a> </div>
			<div id="events"><a href="events.php">Events</a></div>
			<div id="logout"><a href="logout.php">Logout</a></div>
		</div>
		</div>	
		<div id="midarea">
   	    <div class="main_div">
		    <div class="title">Booking Form</div>
		    <?php
		    if(isset($_GET['eId'], $_GET['eventName'], $_GET['eventOrg'])){
			$eventId = $_GET['eId'];
			$eventName = $_GET['eventName'];
			$eventOrg = $_GET['eventOrg'];
			}
		    ?>
    		<form name="bookingForm"  action="php/booking.php?eId=<?php echo $eventId; ?>&eventName=<?php echo $eventName; ?>&eventOrg=<?php echo $eventOrg; ?>" onsubmit="return bookingVal();"  method="post">
			    <div class="input_box">
			    <input type="text" name="email" placeholder="Email Address">
			    </div>
			    <div class="input_box button">
			    <input type="submit" name="submit" value="Book"> 
			    </div>  
			    <div id="eresult"></div> 
			</form>    
		</div>
		</div>
		<div id="botarea" style="background-image: url('images/li.webp'); background-size: cover;">
        	<div id="footer" style="background-image: url('images/li.webp'); background-size: cover;"> <p>Copyright © 2021 Aston University </p>  </div>
    	</div>
		</div>			
	</body>	
</html> 
