package ca.ubc.cpsc304.r3.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionService {
	
	/**
	 * Singleton ConnectionService. Access through getInstance()
	 */
	private static final ConnectionService instance = new ConnectionService();

	private final DataSource dataSource;

	private ConnectionService(){
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context)initContext.lookup("java:/comp/env");
			dataSource = (DataSource)envContext.lookup("jdbc/crazycoollibrary");
		} catch(NamingException e){
			throw new RuntimeException("Could not initialize connectionService. Check your configuration in context.xml", e);
		}
	}

	/**
	 * 
	 * @return a connection to the database. The caller is responsible 
	 * for closing the connection once it is no longer required.
	 * @throws SQLException if a database error occurs
	 */
	public Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}
	
	/**
	 * 
	 * @return
	 */
	public static ConnectionService getInstance(){
		return instance;
	}

}
