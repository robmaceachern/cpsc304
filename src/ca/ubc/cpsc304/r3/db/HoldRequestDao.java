package ca.ubc.cpsc304.r3.db;

// general sql imports
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ca.ubc.cpsc304.r3.dto.HoldRequestDto;

public class HoldRequestDao {
	
	private ConnectionService connService;
	
	public HoldRequestDao(ConnectionService connService){
		this.connService = connService;
	}
	
	public List<HoldRequestDto> getByID(int id) throws SQLException{
		List<HoldRequestDto> queryResult = new ArrayList<HoldRequestDto>();
		Connection conn = null; 
		try {
			conn = connService.getConnection();	
			PreparedStatement ps = conn.prepareStatement(
					"SELECT * "+
					"FROM holdrequest "  +
					"WHERE bid=?"); 
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
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
	
	public void placeByCallNumberAndID(int callNo, int borrowerID) throws SQLException{
		Connection conn = null; 
		try {
			// first get the current date to be used when placing the hold request
			java.util.Date now = new java.util.Date();
			java.sql.Date sqlNow = new java.sql.Date(now.getTime());
			
			conn = connService.getConnection();
			Statement st = conn.createStatement();
			st.executeQuery(
					"insert into holdrequest(bid, callNumber, issuedDate) " + 
					"values("+borrowerID+", "+callNo+", "+sqlNow+")");
			
			
			conn = connService.getConnection();	
			PreparedStatement ps = conn.prepareStatement(
					"insert into holdrequest(bid, callNumber, issuedDate) " + 
					"values(?,?,?)");
			ps.setInt(1, borrowerID);
			ps.setInt(1, callNo);
			ps.setDate(3, sqlNow);
			
			ps.executeUpdate();
		
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
		
		// is there anything we want to return?
		return;
	}
}

