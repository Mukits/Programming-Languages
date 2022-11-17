<?php
/*php code for the sign up process of the user*/ 

    if(isset($_POST["submit"])){  #if submit is pressed in the form then run the following
        
        #setup variables for the create user function
        $dataName = $_POST["name"]; 
        $dataSurname = $_POST["surname"]; 
        $dataEmail = $_POST["email"]; 
        $dataUid = $_POST["uId"]; 
        $dataPhone = $_POST["phone"];
        $dataPwd = $_POST["pwd"];
        $dataPwdRepeat = $_POST["pwdConfirm"];
         
        require_once 'connection.php'; #if the connection fails
        require_once 'function.php'; #file that will contain all the functions
        if (invalidEmail($dataEmail) !== false)          #check if mail is valid
        {
           header("location: ../registration.php?error=Invalid_Email");
           exit();
        }

        if (invalidValue($dataUid) !== false) {            #check that ID number is a numeric value
            header("location: ../registration.php?error=No_Letters_Or_Special_Characters_Allowed_For_ID_Number");
            exit();
        }


        if (invalidValue($dataPhone) !== false) {        #check that phone number VALUE contains only numbers
            header("location:../registration.php?error=No_Letters_Or_Special_Characters_Allowed_For_Phone_Number");
            exit();
        }
        
        if (passVal($dataPwd,$dataPwdRepeat) !== false) {         #check that password confirm is correct equal to password

            header("location:../registration.php?error=pass_dont_match");
            exit();

        }

         
        if (lengthPhone ($dataPhone) !== false) {                                #check phone number lenght
            header("location: ../registration.php?error=checkPhoneLength");
            exit();
         }

        if (ppattern($dataPwd) !==false) {          #check that password is strong

            header("location:../registration.php?error=pass_weak");
            exit();

        }

        
        
        createUser($conn, $dataName, $dataSurname, $dataEmail, $dataPwd, $dataPhone, $dataUid); 
    }else{
        header("location: ../registration.php?registration=unsuccessful"); #go back to registration page if unsuccessful.
        exit(); 
    }

