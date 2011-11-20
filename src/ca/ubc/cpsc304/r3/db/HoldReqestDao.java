package ca.ubc.cpsc304.r3.db;

// general sql imports
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

// import the hold request dto class
import ca.ubc.cpsc304.r3.dto.HoldRequestDto;

public class HoldReqestDao {
	
	private ConnectionService connService;
	
	public HoldReqestDao(ConnectionService connService){
		this.connService = connService;
	}
	
	public List<HoldRequestDto> getByID(int id) throws SQLException{
		List<HoldRequestDto> queryResult = new ArrayList<HoldRequestDto>();
		Connection conn = null; 
		try {
			conn = connService.getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(
					"SELECT * " + 
					"FROM holdrequest " + 
					"WHERE bid="+id);
			while(rs.next()){
				// for each row, put the data in the dto
				// and add it to list of results
				HoldRequestDto dto = new HoldRequestDto();
				dto.setBid(rs.getInt("bid"));
				dto.setHid_(rs.getInt("hid"));
				dto.setCallNumber(rs.getInt("callNumber"));
				dto.setIssuedDate(rs.getDate("issuedDate"));
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

