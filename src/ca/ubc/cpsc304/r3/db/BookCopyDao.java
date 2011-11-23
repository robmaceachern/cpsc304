package ca.ubc.cpsc304.r3.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ca.ubc.cpsc304.r3.dto.BookCopyDto;

public class BookCopyDao {
private ConnectionService connService;
	
	public BookCopyDao(ConnectionService connService){
		this.connService = connService;
	}
	
	public List<BookCopyDto> getCopiesInOrOutByCallNumber(int id) throws SQLException{
		List<BookCopyDto> queryResult = new ArrayList<BookCopyDto>();
		Connection conn = null; 
		try {
			conn = connService.getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(
					"SELECT * " + 
					"FROM bookcopy " + 
					"WHERE callNumber="+id);
			while(rs.next()){
				// for each row, put the data in the dto
				// and add it to list of results
				BookCopyDto dto = new BookCopyDto();
				dto.setCallNumber(rs.getInt("callNumber"));
				dto.setCopyNo(rs.getInt("copyNo"));
				dto.setStatus(rs.getString("status"));
				queryResult.add(dto);
			}
		} catch (SQLException e) {
			// two options here. either don't catch this exception and 
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
}
