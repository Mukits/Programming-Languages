<?php

if(isset($_GET['id'])){              #if id is set then run the following
 
  	#setup eventId variable and start session userID
    $eventId = $_GET['id'];
    session_start();
    $userID = $_SESSION['userID'];

    require_once 'connection.php'; #if the connection fails
    require_once 'function.php'; #file that will contain all the functions

     likeGet($conn,$eventId,$userID);
} else {

     header("location: ../events.php?like=unsuccessful");  #if like is not registered then go back to event page
     exit(); 
}