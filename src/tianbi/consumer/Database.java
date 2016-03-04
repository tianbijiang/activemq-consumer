package tianbi.consumer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Database {

	private static final String userName = "micor_si";
	private static final String password = "micor2015";
	private static final String serverName = "signaldb.ami.local";
	private static final int portNumber = 3306;
	private static final String dbName = "micor_signaldb";
		
	public static Connection conn = null;
	
	public Database() {
		
	}
	
    public static void createDbConnection() throws SQLException {
		try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
        	System.out.println("*ERROR: Driver egistration failed: "+ex);
        }
		Properties connectionProps = new Properties();
		connectionProps.put("user", userName);
		connectionProps.put("password", password);

		conn = DriverManager.getConnection("jdbc:mysql://"
				+ serverName + ":" + portNumber + "/" + dbName,
				connectionProps);
	}
    
	public synchronized static void executeQuery(String querySQL) {
		
	    Statement stmt = null;
	    try {
	        stmt = conn.createStatement();
	        stmt.executeUpdate(querySQL);
	    } catch (SQLException e) {
			e.printStackTrace();
		} finally {
	        if (stmt != null) { try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} }
	    }

	}
}
