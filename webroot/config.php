<?php
	session_start();
	
	$host='localhost';
	$dbuser='root';
	$dbpass='';
	$db='dads';
	
	$col = "mysql:host=$host;dbname=$db";
	
	$db = null;
	
	try
	{
		$db = new PDO($col , $dbuser, $dbpass);
	} 
	catch(PDOException $e)
	{
		echo 'Attenzione: '.$e->getMessage();
	}
?>