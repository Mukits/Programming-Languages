<?php
/* function for the registration, this creates the user and insert into DB table*/
function createUser($conn, $var_name, $var_surname, $var_email, $var_pwd, $var_phone, $var_uId) {

   $sql = "INSERT INTO students (userName, userSurname, userEmail, userPassword, userPhone, userUid) VALUES (?,?,?,?,?,?);";
   $stmt = mysqli_stmt_init($conn);

	if (!mysqli_stmt_prepare($stmt,$sql)){
            header("location: ../registration.php?error=stmtfailed");
            exit(); 
    }
#make the password hashed 
    $hashPass = password_hash($var_pwd, PASSWORD_DEFAULT);
    mysqli_stmt_bind_param($stmt,"ssssss", $var_name, $var_surname, $var_email, $hashPass, $var_phone, $var_uId); 
    mysqli_stmt_execute($stmt); 
    mysqli_stmt_close($stmt);
    header("location: ../index.php?registration=successful");
} 

/*function to check if user is registered in DB*/
function userNameExists($conn, $var_uId, $var_email){
        $sql = "SELECT * FROM students WHERE userUid = ? OR userEmail = ?";
        $stmt = mysqli_stmt_init($conn);
        if (!mysqli_stmt_prepare($stmt,$sql)){
            header("location: ../registration.php?error=stmtfailed");
            exit(); 
        }
        mysqli_stmt_bind_param($stmt, "ss", $var_uId, $var_email); 
        mysqli_stmt_execute($stmt); 

        $resultData = mysqli_stmt_get_result($stmt); 

        if ($row = mysqli_fetch_assoc($resultData)) {
            return $row; 
        }else{
            return false;
        }
        mysqli_stmt_close($stmt); 
    }

/*function that checks that email address and password are correct for successful login*/
function successfulLogIn($conn, $email, $password){
        $databaseFetch = userNameExists($conn,$email,$email); 

        if($databaseFetch === false){
            header("location: ../index.php?error=email_not_found");
            exit(); 
        }
        if(password_verify($password,$databaseFetch["userPassword"]) === false){
            header("location: ../index.php?incorrect_password");
            exit(); 
        } else if (password_verify($password,$databaseFetch["userPassword"]) === true){
            session_start(); 
            $_SESSION["userID"] = $databaseFetch["userID"];
            $_SESSION["userEmail"] = $databaseFetch["userEmail"];
            $_SESSION["usersUid"] = $databaseFetch["userUid"]; 
            $_SESSION["userName"] = $databaseFetch["userName"];
            header("location: ../homepage.php?error=null");
            exit(); 
        }
    }

/* function that gathers data from the DB fron event details table and puts them on the website */
function gatherData($conn){

    if(isset($_GET['id'])) {

        $id = $_GET['id'];
        $sql = "SELECT * FROM eventdetails WHERE eventId ='$id';";
        $out = mysqli_query($conn,$sql);

        if (!$out){
            printf("Error: %s\n", mysqli_error($conn));
            exit();
        }

        while($row = mysqli_fetch_assoc($out)) {
            ?>

            <div id="detailsOrg"><div id="miniTi"><h3>Event Organiser</h3></div><div id="detailsOrgText"><p><?php echo $row['eventOrg'];?></p></div></div>
            <div id="detailsDesc"><div id="miniTi"> <h3>Event Description</h3></div><div id="detailsDescText"><p><?php echo $row['eventDescription'];?></p></div></div>
            <div id="detailsType"><div id="miniTiRight"> <h3>Event Type</h3></div><div id="genText"><p><?php echo $row['eventType'];?> </p></div></div>
            <div id="detailsName"><div id="miniTiLeft">  <h3>Event Name</h3></div><div id="genText"><p><?php echo $row['eventName'];?> </p></div></div>
            <div id="detailsDate"><div id="miniTiRight"><h3>Event Date</h3></div><div id="genText"><p><?php echo $row['eventDate'];?></p></div></div>
            <div id="detailsEmail"><div id="miniTiLeft"><h3>Email Address</h3></div><div id="genText"><p><?php echo $row['orgEmail'];?></p></div></div>
            <div id="detailsNumber"><div id="miniTiRight"><h3>Phone Number</h3></div><div id="genText"><p><?php echo $row['orgPhone'];?></p></div></div>
            <div id="detailsLocation"><div id="miniTiLeft"><h3>Event Location</h3></div><div id="genText"><p><?php echo $row['eventPlace'];?></p></div></div>
            <div id="detailsStart"><div id="miniTiRight"><h3>Start Time</h3></div><div id="genText"><p><?php echo $row['eventStart'];?></p></div></div>
            <div id="detailsEnd"><div id="miniTiLeft"><h3>End Time</h3></div><div id="genText"><p><?php echo $row['eventEnd'];?></p></div></div>
            <div id="bookHere"><a href="Booking.php?eId=<?php echo $row['eventId']?>&eventName=<?php echo urlencode($row['eventName'])?>&eventOrg=<?php echo $row['eventOrg']?>">Book here</a></div></div>
            
            <?php
        }

    }
}
/* function used to gather data from DB   */
function showLink($conn,$type){
    $sql = "SELECT * FROM eventdetails WHERE eventType='$type';";
    $result = mysqli_query($conn, $sql);
    if (!$result){
        printf("Error: %s\n", mysqli_error($conn));
        exit();
    }
    while($row = mysqli_fetch_assoc($result)){
        ?>
        <div id="event"><p> <a href="eventdetails.php?id=<?php echo $row['eventId']; ?>"><?php echo $row['eventName'];?></a> </p> </div>
        <button type='button' name="heart" onClick="location.href='php/like.php?id=<?php echo $row['eventId'];?>';"> like </button>
        <?php
    }
}


/* function used to register a user booking into reservations table in DB  */

function bookingUser($conn,$eventId,$userID,$eventName,$userUid,$userEmail,$eventOrg) {

    $sql = "INSERT INTO reservations (eventId,userID,eventName,userUid,userEmail,eventOrg) VALUES (?,?,?,?,?,?);";
    $stmt = mysqli_stmt_init($conn);

    if (!mysqli_stmt_prepare($stmt,$sql)){
        header("location: ../Booking.php?error=stmtfailed");
        exit(); 
    }
    mysqli_stmt_bind_param($stmt,"ssssss", $eventId,$userID,$eventName,$userUid,$userEmail,$eventOrg); 
    mysqli_stmt_execute($stmt); 
    mysqli_stmt_close($stmt);
    header("location: ../homepage.php?booking=successful");
} 
 

/*function used to register the likes of users into likeCount table in DB*/

function likeGet($conn,$eventId,$userID){
    $sql = "INSERT INTO likeCount (eventId, userID) VALUES (?,?);";
    $stmt = mysqli_stmt_init($conn);

    if (!mysqli_stmt_prepare($stmt,$sql)){
            header("location: ../Booking.php?error=stmtfailed");
            exit(); 
    }
    mysqli_stmt_bind_param($stmt,"ss", $eventId,$userID); 
    mysqli_stmt_execute($stmt); 
    mysqli_stmt_close($stmt);
    header("location: ../Booking.php?like=successful");

}


 /* function for email validation  */

function invalidEmail ($email){                  
    if (!preg_match('/@aston.ac.uk/', $email)){
        return true;
    }
    else {
        return false;
    }
}


/* function for phone number validation  */
function invalidValue ($phone){
    if (!preg_match("/^[0-9]*$/", $phone)){
        return true;
    }
    else{
        return false;
    }
}

/* pattern password  */
function ppattern($var_pwd){

if(!preg_match('/^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?[0-9])[a-zA-Z0-9]{8,}$/', $var_pwd)){


    return true;
}else {

    return false;
}


}


/* password validation */

function passVal($var_pwd,$var_pwdRepeat){

    if($var_pwd!==$var_pwdRepeat) {


        return true;
    }else {

        return false;
    }

}


/* PHONE NUMBER LENGHT VALIDATION */
function lengthPhone ($var_phone){
if (!preg_match('/(^\d{10}$)/',$var_phone)){
    return true;
} else {
    return false;
}
}




























