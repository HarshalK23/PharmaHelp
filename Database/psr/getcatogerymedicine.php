	<?php
$con=mysqli_connect("localhost:3306","root","","peopledata") or die ("Could not connect");
	$where = $_REQUEST['catogery'];
	$jsonData = array();
	$query="SELECT * FROM medicine where catogery='$where'";
	$result = mysqli_query($con,$query);

	if($result){
	
		 $resultArray = array();
        $tempArray = array();

        while($row = $result->fetch_object())
        {
	$newa=array();
	   $row_encoding = $row->name;
	   array_push($newa, $row_encoding); 
	   $row_encoding =$row->description;
	   array_push($newa, $row_encoding); 
	   $row_encoding = $row->catogery;
	   array_push($newa, $row_encoding); 
	   $row_encoding =$row->price;
	   array_push($newa, $row_encoding); 
           $row_encoding = (array) base64_encode($row->photo); 
	   array_push($newa, $row_encoding); 	
	   
	   array_push($resultArray, $newa); 
        }

        echo json_encode($resultArray);



	}
	else{
		echo "error";
	}
	//json_encode()
	?>