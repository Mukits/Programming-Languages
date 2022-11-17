<?php
/*this doc will have only
php code, which will be used to handle the database*/

#setup for the server info 
$serverName = "localhost";
$dbUserName = "u-200080734";
$dbPassWord = "QeMcgEuPcPHprba";
$dbName = "u_200080734_db";

#estabilish connection with the server 
$conn = mysqli_connect($serverName, $dbUserName, $dbPassWord, $dbName); 

#if the connection fails 
if(!$conn){
	die("The connection failed, the error is: " . mysqli_connect_error()); 
}