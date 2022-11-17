<?php

if(isset($_POST['submit'])){         #check if submit is pressed in the form

    #setup variables for the bookingUser function
	$userEmail = $_POST['email'];          
	$eventId = $_GET['eId'];             
	$eventName = $_GET['eventName'];      
	$eventOrg = $_GET['eventOrg'];         
    session_start();          
    $userID = $_SESSION['userID'];          
    $userUid =  $_SESSION['usersUid'];

    require_once 'connection.php'; #if the connection fails
    require_once 'function.php'; #file that will contain all the functions
    
    if (invalidEmail($userEmail) !== false)          #check if mail is valid
    {
       header("location: ../Booking.php?error=invalid_email");
       exit();
    }

    bookingUser($conn,$eventId,$userID,$eventName,$userUid,$userEmail,$eventOrg);
}else
{
	header("location: ../homepage.php?booking=failed"); #go to homepage if code above didnt work and booking is failed
	exit(); 
}
