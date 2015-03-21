<?php
	require_once('config.php');
	
	// LOGOUT
	if ( isset($_GET['logout']))
	{
		session_destroy();
		Header("Location: index.php");
	}
?>
<!doctype html>
<html>
	<head>
		<title>DADS</title>
		<script src="Chart.js"></script>
		<link rel="stylesheet" href="style.css">
	</head>
	<body>
		<?php
			if ( isset($_SESSION['username']))
			{
		?>
		
		<div id="header">
			<div id="home">
				<a href="?">DADs Pannello di controllo</a>
			</div>
			<div id="userbar">
				BENVENUTO <?=$_SESSION['username']?> | 
				<a href="?settings=1">IMPOSTAZIONI</a> 
				<a href="?support=1">SUPPORTO</a> 
				<a href="?logout=1">LOGOUT</a>
				
			</div>
		</div>
		<div id="navbar">
			<a href="index.php">AGGIORNA</a> 
		</div>
		<div id="container">
			<table>
				<tr>
					<td id="cell1" class="cell">
						Life time:</br>
						<canvas id="canvas1" height="200" width="300"></canvas>
					</td>
					<td id="cell2" class="cell">
						From Nation:</br>
						<canvas id="canvas2" height="200" width="300"></canvas>
					</td>
					<td id="cell3" class="cell">
						Factor Risk:</br>
						<canvas id="canvas3" height="200" width="300"></canvas>
					</td>
				</tr><tr>
					<td id="cell4" class="cell">C</td>
					<td id="cell5" class="cell">D</td>
					<td id="cell6" class="cell">F</td>
				</tr>
			</table>
		</div>
		
		<script>
			var randomScalingFactor = function(){ return Math.round(Math.random()*100)};
			var lineChartData = {
				labels : ["January","February","March","April","May","June","July"],
				datasets : [
					{
						label: "My First dataset",
						fillColor : "rgba(220,220,220,0.2)",
						strokeColor : "rgba(220,220,220,1)",
						pointColor : "rgba(220,220,220,1)",
						pointStrokeColor : "#fff",
						pointHighlightFill : "#fff",
						pointHighlightStroke : "rgba(220,220,220,1)",
						data : [randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor()]
					},
					{
						label: "My Second dataset",
						fillColor : "rgba(151,187,205,0.2)",
						strokeColor : "rgba(151,187,205,1)",
						pointColor : "rgba(151,187,205,1)",
						pointStrokeColor : "#fff",
						pointHighlightFill : "#fff",
						pointHighlightStroke : "rgba(151,187,205,1)",
						data : [randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor()]
					}
				]

			}

		window.onload = function(){
			var ctx = document.getElementById("canvas1").getContext("2d");
			window.myLine = new Chart(ctx).Line(lineChartData, {
				responsive: true
			});
		}
		var doughnutData = [
		<?php
			$sql = "SELECT COUNT(ipmittenti.idIp) as n, nome, fattoreRischio
					FROM ipmittenti, nazioni,classiip 
					WHERE ipmittenti.idClasse = classiip.idClasse AND classiip.idNazione = nazioni.idNazione
					GROUP BY nazioni.idNazione";
			$n = 0;
			foreach( $db->query($sql) as $row )
			{
				if ( $n == 0 )
					echo '{';
				else
					echo ',{';
				echo "value: " . $row['n'] . ",";
			?>
				label: "<?=$row['nome']?>",
				color:
			<?php
				if ( $row['fattoreRischio'] > 1 )
					echo '"#F7464A",';
				else
					echo '"#00FF00",';
			?>
				highlight: "#B2B2B2	"
			<?php
				$n+=1;
				echo '}';
			}
		?>
			];

			var randomScalingFactor = function(){ return Math.round(Math.random()*100)};

			var barChartData = {
				labels : ["14:00","14:10","14:10"],
				datasets : [
					{
						fillColor : "rgba(220,220,220,0.5)",
						strokeColor : "rgba(220,220,220,0.8)",
						highlightFill: "rgba(220,220,220,0.75)",
						highlightStroke: "rgba(220,220,220,1)",
						data : [ 3, 5, 2						]
					},
					{
						fillColor : "rgba(151,18,18,0.5)",
						strokeColor : "rgba(151,187,205,0.8)",
						highlightFill : "rgba(151,187,205,0.75)",
						highlightStroke : "rgba(151,187,205,1)",
						data : [3, 15, 5]
					}
				]

			}
			window.onload = function(){
				var ct2 = document.getElementById("canvas2").getContext("2d");
				var ct1 = document.getElementById("canvas1").getContext("2d");
				var ct3 = document.getElementById("canvas3").getContext("2d");
				
				window.myLine = new Chart(ct1).Line(lineChartData, {responsive: true});
				window.myDoughnut = new Chart(ct2).Doughnut(doughnutData, {responsive : true});
				window.myBar = new Chart(ct3).Bar(barChartData, {responsive : true });
			};

		</script>
		</div>
		<?php
			} else {
				
		?>
			<center>
				<form action="login.php" method="post">
					Username: <input type="text" name="username" />
					Password: <input type="password" name="password" />
					<input type="submit" value="Logga">
				</form>
			</center>
		<?php
			}
		?>
	</body>
</html>
