package ca.ubc.cpsc304.r3.db;

//general sql imports
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

// import the hold request dto class
import ca.ubc.cpsc304.r3.dto.BorrowingDto;

public class BorrowingDao {
	
	private ConnectionService connService;
	
	public BorrowingDao(ConnectionService connService){
		this.connService = connService;
	}
	
	public List<BorrowingDto> getUnpaidByID(int id) throws SQLException{
		List<BorrowingDto> queryResult = new ArrayList<BorrowingDto>();
		Connection conn = null; 
		try {
			conn = connService.getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(
					"SELECT * " + 
					"FROM borrowing " + 
					"WHERE inDate IS NULL AND " +
					"bid="+id);	
			while(rs.next()){
				// for each row, put the data in the dto
				// and add it to list of results
				BorrowingDto dto = new BorrowingDto();
				dto.setBorid(rs.getInt("borid"));
				dto.setBid(rs.getInt("bid"));
				dto.setCallNumber(rs.getInt("callNumber"));
				dto.setCopyNo(rs.getInt("copyNo"));
				dto.setOutDate(rs.getDate("outDate"));
				dto.setInDate(rs.getDate("inDate"));
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

