package ca.ubc.cpsc304.r3.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ca.ubc.cpsc304.r3.dto.HasSubjectDto;

public class HasSubjectDao {
private ConnectionService connService;
	
	public HasSubjectDao(ConnectionService connService){
		this.connService = connService;
	}
	
	public List<HasSubjectDto> searchSubjectByKeyword(String keyword) throws SQLException{
		List<HasSubjectDto> queryResult = new ArrayList<HasSubjectDto>();
		Connection conn = null; 
		try {
			conn = connService.getConnection();	
			PreparedStatement ps = conn.prepareStatement(
					"SELECT * "+
					"FROM hassubject "  +
					"WHERE subject like '%?%'?"); // matches any subject that contains <keyword>
			ps.setString(1, keyword);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				// for each row, put the data in the dto
				// and add it to list of results
				HasSubjectDto dto = new HasSubjectDto();
				dto.setCallNumber(rs.getInt("callNumber"));
				dto.setSubject(rs.getString("subject"));
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
