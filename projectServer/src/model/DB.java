package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

public class DB {
	private static DB database = null;
    private static Connection conn = null;
    private static Statement stat = null;

    private final static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private final static String JDBC_URL = "jdbc:mysql://localhost:3306/project";

    private final static String userName = "root";
    private final static String userPassword = "root";

    public DB() {
        try {
            Class.forName(JDBC_DRIVER);
            try {
                conn = (Connection) DriverManager.getConnection(JDBC_URL, userName, userPassword);
                stat = (Statement) conn.createStatement();
            } catch (SQLException ex) {
            	ex.printStackTrace();
            }
        } catch (ClassNotFoundException ex) {
        	ex.printStackTrace();
        }
    }

    public synchronized void closeConnection() {
        try {
            conn.close();
        } catch (SQLException ex) {
            // TODO
            ex.printStackTrace();
        }
    }

    public static synchronized DB getDBInstance() {
        if (database == null) {
            database = new DB();
        }
        return database;
    }

    // update information in the database
    public synchronized void executeUpdate(String query) {
        try {
            stat.executeUpdate(query);
        } catch (SQLException ex) {
        	//ex.printStackTrace();
        }
    }

    // read information from database
    public synchronized ResultSet executeQuery(String query) {
        ResultSet res = null;
        try {
            res = stat.executeQuery(query);
        } catch (SQLException ex) {
        	ex.printStackTrace();
        }
        return res;
    }
}
