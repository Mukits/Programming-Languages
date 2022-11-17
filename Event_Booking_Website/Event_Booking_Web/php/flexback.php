<?php                         
if(isset($_GET['id'])){                #if id number is set, then get id number and run the following

#change background according to id number of the page
if($_GET['id'] ==1){

?> 
<style>
  div {
        background-image: url('images/golf.jpg');
        background-size: cover;
        background-repeat: no-repeat;
      }
</style>
<?php

}elseif($_GET['id'] ==2){

?>
<style>
  div {
        background-image: url('images/cricket.jpg');
  			background-size: cover;
  		  background-repeat: no-repeat;
      }
</style>

<?php

}elseif($_GET['id'] ==3){

?>

<style>
  div {
        background-image: url('images/football.jpg');
        background-size: cover;
        background-repeat: no-repeat;
      }
</style>

<?php

}elseif($_GET['id'] ==4){

?>

<style>
  div {
        background-image: url('images/water.jpg');
        background-size: cover;
        background-repeat: no-repeat;
      }
</style>

<?php

}elseif($_GET['id'] ==5){

?>

<style>
  div {
        background-image: url('images/literature.jpg');
        background-size: cover;
        background-repeat: no-repeat;
      }
</style>

<?php

}elseif($_GET['id'] ==6){

?>

<style>
  div {
        background-image: url('images/art.jpg');
        background-size: cover;
        background-repeat: no-repeat;
      }
</style>

<?php

}elseif($_GET['id'] ==7){

?>

<style>
  div {
        background-image: url('images/cyber.jpg');
        background-size: cover;
        background-repeat: no-repeat;
      }
</style>

<?php

}elseif($_GET['id'] ==8){

?>

<style>
  div {
        background-image: url('images/society.webp');
        background-size: cover;
        background-repeat: no-repeat;
      }
</style>

<?php

}elseif($_GET['id'] ==9){

?>

<style>
  div {
        background-image: url('images/social.jpg');
        background-size: cover;
        background-repeat: no-repeat;
      }
</style>

<?php
}



}