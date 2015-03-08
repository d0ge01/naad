package it.salvatorecriscione.naad.jiny;

import java.sql.*;

public class Dbmg {
	Connection conn = null;
	
	PreparedStatement pst = null;

	// Valori di default
    String url = "jdbc:mysql://localhost:3306/";
    String user = "root";
    String pass = "";

	public Dbmg(String username, String password, String db)
	{
		user = username;
		pass = password;
		url = url + db;
		
		try 
		{
			conn = DriverManager.getConnection(url, user, password);
		} catch ( SQLException e)
		{
			it.salvatorecriscione.naad.debug.Error.error(e.toString());
			return;
		}
	}
	
	public int executeQuery(String query)
	{
		try {
			pst = conn.prepareStatement(query);
			pst.executeUpdate();
			return 1;
		} catch (SQLException e) {
			it.salvatorecriscione.naad.debug.Error.error(e.toString());
			return -1;
		}
	}
	
	
}
