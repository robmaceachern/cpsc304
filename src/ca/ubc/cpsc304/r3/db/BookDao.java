package ca.ubc.cpsc304.r3.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BookDao {

	private ConnectionService connService;
	
	public BookDao(ConnectionService connService){
		this.connService = connService;
	}
	
	/**
	 * A sample query to demonstrate how the data access will work.
	 * @return a list of database table names.
	 * @throws SQLException If there was a database error
	 */
	public List<TableDto> getDatabaseTableNames() throws SQLException{
		List<TableDto> queryResult = new ArrayList<TableDto>();
		Connection conn = null; 
		try {
			conn = connService.getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(
					"SELECT table_name, table_rows " + 
					"FROM information_schema.tables " + 
					"WHERE table_schema='crazycoollibrary'");
			while(rs.next()){
				// for each row, put the data in the dto
				// and add it to list of results
				TableDto dto = new TableDto();
				dto.setTableName(rs.getString("table_name"));
				dto.setNumRows(rs.getInt("table_rows"));
				queryResult.add(dto);
			}
		} catch (SQLException e) {
			// two options here. either don't catch this expection and 
			// make the caller handle it, or wrap it in a more 
			// descriptive exception depending on the situation.
			// I'll just throw it
			throw e;
			
		} finally {
			// don't forget to close the connection
			// when you're done with it
			if(conn != null){
				conn.close();
			}
		}
		return queryResult;
	}
	
	
	/**
	 * 
	 * Encapsulates the results of my table query.
	 * 
	 * Each Dto should be in it's own class file, I'm just 
	 * doing this for a quick example
	 *
	 */
	public static class TableDto {
		
		private String tableName;
		
		private int numRows;
		
		public TableDto(){}

		public int getNumRows() {
			return numRows;
		}

		public void setNumRows(int numRows) {
			this.numRows = numRows;
		}

		public String getTableName() {
			return tableName;
		}

		public void setTableName(String tableName) {
			this.tableName = tableName;
		}
	}
}
