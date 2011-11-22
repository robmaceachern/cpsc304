package ca.ubc.cpsc304.r3.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ca.ubc.cpsc304.r3.dto.HasAuthorDto;

public class HasAuthorDao {
private ConnectionService connService;
	
	public HasAuthorDao(ConnectionService connService){
		this.connService = connService;
	}
	
	public List<HasAuthorDto> searchAuthorByKeyword(String keyword) throws SQLException{
		List<HasAuthorDto> queryResult = new ArrayList<HasAuthorDto>();
		Connection conn = null; 
		try {
			conn = connService.getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(
					"SELECT * " + 
					"FROM hasauthor " + 
					"WHERE author like '%"+keyword+"%'"); // matches any author that contains <keyword>
			while(rs.next()){
				// for each row, put the data in the dto
				// and add it to list of results
				HasAuthorDto dto = new HasAuthorDto();
				dto.setCallNumber(rs.getInt("callNumber"));
				dto.setAuthor(rs.getString("author"));
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
