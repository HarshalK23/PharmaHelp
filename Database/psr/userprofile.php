<?php
$con=mysqli_connect("localhost:3306","root","","peopledata") or die ("Could not connect");

$username = $_POST['username'];
$sql="Select * from user where username='$username'";
$result = mysqli_query($con,$sql);
$row = mysqli_fetch_array($result);

	echo $row[0];
	echo $row[1];

mysqli_close($con);

?>