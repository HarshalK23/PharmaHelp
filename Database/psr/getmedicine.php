	<?php
$con=mysqli_connect("localhost:3306","root","","peopledata") or die ("Could not connect");	
	$jsonData = array();
	$query="SELECT * FROM medicine";
	$result = mysqli_query($con,$query);

	if($result){
	while ($array = mysqli_fetch_row($result)) {
		$jsonData[] = $array;
	}

	echo json_encode($jsonData);
	}
	else{
		echo "error";
	}
	//json_encode()
	?>