package ca.ubc.cpsc304.r3.db;

//general sql imports
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ca.ubc.cpsc304.r3.DaoUtility;
import ca.ubc.cpsc304.r3.dto.BorrowingDto;
import ca.ubc.cpsc304.r3.dto.CheckedOutBookDto;

public class BorrowingDao {
	
	private ConnectionService connService;
	
	public BorrowingDao(ConnectionService connService){
		this.connService = connService;
	}
	
	public List<BorrowingDto> getBorrowedByID(int id) throws SQLException{
		List<BorrowingDto> queryResult = new ArrayList<BorrowingDto>();
		Connection conn = null; 
		try {
			conn = connService.getConnection();	
			PreparedStatement ps = conn.prepareStatement(
					"SELECT * "+
					"FROM borrowing "  +
					"WHERE inDate IS NULL AND " +
					"bid=?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			
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

	public List<CheckedOutBookDto> generateCheckedOutBooksReport(String subject) throws SQLException {
		
		Connection conn = null;
		try {
			
			// is subject valid?
			boolean hasSubject = (subject != null && !subject.isEmpty());
			
			String query =  "SELECT DISTINCT BC.callNumber, BC.copyNo, BK.title, BORRWRING.outDate, BT.bookTimeLimit " +
							"FROM bookcopy BC, borrowing BORRWRING, borrower B, book BK, hassubject HS, borrowertype BT " + 
							"WHERE BC.status='out' AND BC.callNumber=BORRWRING.callNumber AND BC.copyNo=BORRWRING.copyNo AND BORRWRING.bid=B.bid AND B.btype=BT.btype AND BK.callNumber=BC.callNumber ";
			if(hasSubject){
				query = query +
							"AND BC.callNumber=HS.callNumber AND HS.subject=? ";
			}
			query = query + "ORDER BY callNumber;";
			
			conn = this.connService.getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
			
			// set the subject parameter if subject is valid
			if(hasSubject){
				ps.setString(1, subject);
			}
			
			ResultSet rs = ps.executeQuery();
			
			List<CheckedOutBookDto> results = new ArrayList<CheckedOutBookDto>();
			
			while(rs.next()){
				
				CheckedOutBookDto dto = new CheckedOutBookDto();
				dto.setCallNumber(rs.getInt("BC.callNumber"));
				dto.setCopyNo(rs.getInt("BC.copyNo"));
				dto.setTitle(rs.getString("BK.title"));
				
				Date outDate = rs.getDate("BORRWRING.outDate");
				dto.setOutDate(outDate);
				
				int timeLimitWeeks = Integer.parseInt(rs.getString("BT.bookTimeLimit"));
				
				// dueDate = checkoutDate + (num of weeks allowed * length of week)
				dto.setDueDate(new Date(outDate.getTime() + (timeLimitWeeks * DaoUtility.WEEK)));
				
				// add it to list
				results.add(dto);
			}
			
			return results;
			
		} finally {
			
			if (conn != null){
				
				conn.close();
				
			}
		}
	}
}

