package ca.ubc.cpsc304.r3.db;

//general sql imports
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ca.ubc.cpsc304.r3.dto.FineDetailedDto;
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
	
	public List<FineDetailedDto> getDetailedByFid(int id) throws SQLException{
		List<FineDetailedDto> queryResult = new ArrayList<FineDetailedDto>();
		Connection conn = null; 
		try {			
			conn = connService.getConnection();	
			PreparedStatement ps = conn.prepareStatement(
					"SELECT F.fid, F.amount, F.issuedDate, F.paidDate, F.borid, BO.callNumber, B.title, B.mainAuthor " + 
					"FROM fine F, book B, borrowing BO " + 
					"WHERE fid=? AND F.borid=BO.borid AND BO.callNumber=B.callNumber");

			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
							
			while(rs.next()){
				// for each row, put the data in the dto
				// and add it to list of results
				FineDetailedDto dto = new FineDetailedDto();
				dto.setFid(rs.getInt("F.fid"));
				dto.setAmount(rs.getInt("F.amount"));
				dto.setIssuedDate(rs.getDate("F.issuedDate"));
				dto.setPaidDate(rs.getDate("F.paidDate"));
				dto.setBorid(rs.getInt("F.borid"));
				dto.setCallNumber(rs.getInt("BO.callNumber"));
				dto.setTitle(rs.getString("B.title"));
				dto.setMainAuthor(rs.getString("B.mainAuthor"));
				queryResult.add(dto);
			}
		} finally {
			// don't forget to close the connection
			// when you're done with it
			if(conn != null){
				conn.close();
			}
		}
		return queryResult;
	}
	
	public List<FineDetailedDto> getDetailedUnpaid(int id) throws SQLException{
		List<FineDetailedDto> queryResult = new ArrayList<FineDetailedDto>();
		Connection conn = null; 
		try {			
			conn = connService.getConnection();	
			PreparedStatement ps = conn.prepareStatement(
					"SELECT F.fid, F.amount, F.issuedDate, F.paidDate, F.borid, BO.callNumber, B.title, B.mainAuthor " + 
					"FROM fine F, book B, borrowing BO " + 
					"WHERE BO.bid=? AND F.borid=BO.borid AND BO.callNumber=B.callNumber AND F.paidDate IS NULL");

			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
							
			while(rs.next()){
				// for each row, put the data in the dto
				// and add it to list of results
				FineDetailedDto dto = new FineDetailedDto();
				dto.setFid(rs.getInt("F.fid"));
				dto.setAmount(rs.getInt("F.amount"));
				dto.setIssuedDate(rs.getDate("F.issuedDate"));
				dto.setPaidDate(rs.getDate("F.paidDate"));
				dto.setBorid(rs.getInt("F.borid"));
				dto.setCallNumber(rs.getInt("BO.callNumber"));
				dto.setTitle(rs.getString("B.title"));
				dto.setMainAuthor(rs.getString("B.mainAuthor"));
				queryResult.add(dto);
			}
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
					" WHERE fid=? AND paidDate is NULL");

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

