<?php
$con=mysqli_connect("localhost:3306","root","","peopledata") or die ("Could not connect");
$username = $_REQUEST['username'];
$password = $_REQUEST['password'];
$role = $_REQUEST['role'];
$email = $_REQUEST['email'];



$sql="INSERT INTO user (username, role, password,  email) VALUES ('$username','$role', '$password','$email')";
$response=mysqli_query($con,$sql);
if ($response)
{
   echo $response;
}
else
{
	echo "error";
}
mysqli_close($con);

?>