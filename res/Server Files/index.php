<!DOCTYPE html>
<html>
	<head>
	<title>GPS Table</title>
	</head>
    <body>
		<script>
			function loadMap() {
				var table = document.getElementsByTagName("table")[0].children[0];
				var numRows = table.children.length - 1;
				var latitude = table.children[numRows].children[2].innerHTML;
				var longitude = table.children[numRows].children[3].innerHTML;
				var mapUrl = "http://maps.google.com/maps/api/staticmap?center=" + latitude + "," + longitude + "&zoom=14&size=400x300&sensor=false";
				var mapImg = document.createElement("IMG");
				mapImg.setAttribute("src", mapUrl);
				mapImg.setAttribute("style", "width: 800px; height: 800px;");
				document.body.appendChild(mapImg);
	
			}
		</script>
        <center>
			<button id="mapBtn" onclick="loadMap()">Load Map</button>
            <h1>Database Table</h1>
            <br>
            <br>
            <?php  
                $link = mysqli_connect("danu2.it.nuigalway.ie","mydb1124","mydb112432","mydb1124") or die("Error " . mysqli_error($link));
                $query1 = "SELECT * FROM GPSData" or die("Error " . mysqli_error($link));
                $result1 = $link->query($query1);
				
				echo "GPS Data";
				echo "<br>";
				echo "<br>";
				echo "<div id='address'></div>";
				echo "<br>";
                echo "<table border='1' cellpadding='2' cellspacing='2'";
                echo "<tr><td>ID</td><td>USER ID</td><td>Latitude</td><td>Longitude</td><td>Timestamp</td>";
                while ($row = mysqli_fetch_array($result1)) 
                {
                    echo "<tr>";
                    echo "<td>" . $row["ID"] . "</td>";
                    echo "<td>" . $row["USERID"] . "</td>";                    
                    echo "<td>" . $row["LATITUDE"] . "</td>";
		    echo "<td>" . $row["LONGITUDE"] . "</td>";
                    echo "<td>" . $row["TIMESTAMP"] . "</td>";
                    echo "</tr>";
                }
				echo "</table>";
				echo '<script>
					var xhr = new XMLHttpRequest();
					xhr.onreadystatechange = function() {
					if (xhr.readyState == XMLHttpRequest.DONE) {
					var json = xhr.responseText;
					var geo = JSON.parse(json);
					var address = geo.results[0].formatted_address;
					document.getElementById("address").innerHTML = address;
					}
					}
					var table = document.getElementsByTagName("table")[0].children[0];
					var numRows = table.children.length - 1;
					var latitude = table.children[numRows].children[2].innerHTML;
					var longitude = table.children[numRows].children[3].innerHTML;
					xhr.open(\'GET\', \'http://maps.googleapis.com/maps/api/geocode/json?latlng=\' + latitude + \',\' + longitude + \'&sensor=false\', true);
					xhr.send(null);
				   </script>';
            ?>
        </center>
	</body>
</html>
