package ca.ubc.cpsc304.r3.db;

//general sql imports
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

// import the hold request dto class
import ca.ubc.cpsc304.r3.dto.FineDto;

public class FineDao {
	
	private ConnectionService connService;
	
	public FineDao(ConnectionService connService){
		this.connService = connService;
	}
	
	public List<FineDto> getUnpaidByID(int id) throws SQLException{
		List<FineDto> queryResult = new ArrayList<FineDto>();
		Connection conn = null; 
		try {
			conn = connService.getConnection();	
			PreparedStatement ps = conn.prepareStatement(
					"SELECT * " + 
					"FROM fine " + 
					"WHERE paidDate IS NULL AND " +
					"borid IN " +
					"(SELECT borid " +
					"FROM borrowing " +
					"WHERE bid=?)");

			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				// for each row, put the data in the dto
				// and add it to list of results
				FineDto dto = new FineDto();
				dto.setFid(rs.getInt("fid"));
				dto.setAmount(rs.getInt("amount"));
				dto.setIssuedDate(rs.getDate("issuedDate"));
				dto.setPaidDate(rs.getDate("paidDate"));
				dto.setBorid(rs.getInt("borid"));
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
	
	public List<FineDto> getByFid(int id) throws SQLException{
		List<FineDto> queryResult = new ArrayList<FineDto>();
		Connection conn = null; 
		try {			
			conn = connService.getConnection();	
			PreparedStatement ps = conn.prepareStatement(
					"SELECT * " + 
					"FROM fine " + 
					"WHERE fid=?");

			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
							
			while(rs.next()){
				// for each row, put the data in the dto
				// and add it to list of results
				FineDto dto = new FineDto();
				dto.setFid(rs.getInt("fid"));
				dto.setAmount(rs.getInt("amount"));
				dto.setIssuedDate(rs.getDate("issuedDate"));
				dto.setPaidDate(rs.getDate("paidDate"));
				dto.setBorid(rs.getInt("borid"));
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
	
	public int payByFineID(int id) throws SQLException{
		Connection conn = null; 
		try {
			// first get the current date to be used when updating paidDate
			java.util.Date now = new java.util.Date();
			java.sql.Date sqlNow = new java.sql.Date(now.getTime());
			
			conn = connService.getConnection();	
			PreparedStatement ps = conn.prepareStatement(
					"UPDATE fine " + 
					"SET paidDate=?" +
					" WHERE fid=?");

			ps.setDate(1, sqlNow);
			ps.setInt(2, id);
			
			int rowsUpdated = ps.executeUpdate();
			
			return rowsUpdated;
			
		} finally {
			// don't forget to close the connection
			// when you're done with it
			if(conn != null){
				conn.close();
			}
		}
	}
}

