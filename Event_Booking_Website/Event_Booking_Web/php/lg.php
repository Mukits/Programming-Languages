<?php

	if(isset($_POST["submit"])){          #check if submit button is pressed
		#setup variables for function
		$email = $_POST["email"];
		$password = $_POST["password"];



		require_once 'connection.php'; #if the connection fails
	    require_once 'function.php'; #file that will contain all the functions
	    

	

	    successfulLogIn($conn, $email, $password);
	}else{

		header("location: ../index.php?login=failed"); #go to index page if login unsuccessful
	    exit(); 
	}

