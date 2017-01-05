<?php
$con=mysqli_connect("localhost:3306","root","","peopledata") or die ("Could not connect");

$username = $_REQUEST['username'];
$sql="Select password from user where username='$username'";
$result = mysqli_query($con,$sql);
$row = mysqli_fetch_array($result);
$data = $row[0];
if($data)
	echo $data;
else
	echo "error";

mysqli_close($con);

?>