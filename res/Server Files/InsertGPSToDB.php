<?php
	$db = @mysql_connect('danu2.it.nuigalway.ie',
    'mydb1124', 'mydb112432');
    
if (!$db) {
    die('Could not connect: ' . mysql_error());
}
	@mysql_select_db('mydb1124');

	$sql = "INSERT INTO GPSData (USERID, LONGITUDE, LATITUDE) VALUES ('{$_POST['USERID']}', '{$_POST['LONGITUDE']}','{$_POST['LATITUDE']}');";
	$success = mysql_query($sql);
	if ($success){
		echo "GPS coordinates successfully submitted!";
	}else{
		echo "coordinates were <b>NOT</b> submitted.";
	}
?>