<?php
	require_once('config.php');

	if ( isset($_POST['username']) && isset($_POST['password']) )
	{
		$sql = "SELECT * FROM users where username = '" . $_POST['username'] . "' AND password = '" . md5($_POST['password']) . "';";

		$n = 0;
		$id = -1;
		foreach( $db->query($sql) as $row )
		{
			$id = $row['id'];
			$n += 1;
		}
		
		$error = "";
		if ( $n == 1 )
		{
			$_SESSION['username'] = $_POST['username'];
			$_SESSION['password'] = $_POST['password'];
			$_SESSION['id'] = $id;
			
		} else {
			$error = "?error=1";
		}
		
		Header("Location: index.php" . $error);
	}
?>