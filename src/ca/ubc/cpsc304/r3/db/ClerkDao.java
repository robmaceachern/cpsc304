package ca.ubc.cpsc304.r3.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.sql.Date;

import ca.ubc.cpsc304.r3.DaoUtility;
import ca.ubc.cpsc304.r3.dto.BookCopyDto;
import ca.ubc.cpsc304.r3.dto.BorrowerDto;
import ca.ubc.cpsc304.r3.dto.OverdueDto;

public class ClerkDao {
	private ConnectionService connServ;

	public ClerkDao() {
	}

	public ClerkDao(ConnectionService cs) {
		connServ = cs;
	}

	// Add a new borrower to the system.
	public void addBorrower(BorrowerDto bd) throws SQLException {
		Connection conn = null;
		conn = connServ.getConnection();
		PreparedStatement ps = conn
				.prepareStatement("INSERT into Borrower "
						+ "(password, name, phone, address, emailAddress, sinOrStNo, expiryDate, btype) "
						+ "values (?,?,?,?,?,?,?,?)");
		ps.setString(1, bd.getPassword());
		ps.setString(2, bd.getName());
		ps.setInt(3, bd.getPhone());
		ps.setString(4, bd.getAddress());
		ps.setString(5, bd.getEmail());
		ps.setInt(6, bd.getSin());
		ps.setDate(7, DaoUtility.makeDate(DaoUtility.YEAR));
		ps.setString(8, bd.getType());
		ps.executeUpdate();
		if (conn != null)
			conn.close();
	}

	// Checks for all overdue items
	public List<OverdueDto> checkOverdue() throws SQLException {

		Connection conn = null;
		conn = connServ.getConnection();
		Statement st = conn.createStatement();
		ResultSet rs = null;
		List<OverdueDto> queryResults = new ArrayList<OverdueDto>();

		// Students with overdue
		Date overDate = DaoUtility.makeDate(-2 * DaoUtility.WEEK);
		String overDateS = DaoUtility.convertToSQLvalue(overDate);
		rs = st.executeQuery("SELECT B.callNumber, L.borid, U.name, U.btype, B.title, U.emailAddress "
				+ "FROM Book B, Borrower U, Borrowing L "
				+ "WHERE U.btype='student' AND U.bid=L.bid AND B.callnumber=L.callNumber AND L.outDate <"
				+ overDateS);
		while (rs.next()) {
			OverdueDto dto = new OverdueDto();
			dto.setCallNumber(rs.getInt("callNumber"));
			dto.setBorid(rs.getInt("borid"));
			dto.setName(rs.getString("name"));
			dto.setType(rs.getString("btype"));
			dto.setTitle(rs.getString("title"));
			dto.setEmail(rs.getString("emailAddress"));
			queryResults.add(dto);
		}

		// Faculty with overdue
		overDate = DaoUtility.makeDate(-6 * DaoUtility.WEEK);
		overDateS = DaoUtility.convertToSQLvalue(overDate);
		rs = st.executeQuery("SELECT B.callNumber, L.borid, U.name, U.btype, B.title, U.emailAddress "
				+ "FROM Book B, Borrower U, Borrowing L "
				+ "WHERE U.btype='faculty' AND U.bid=L.bid AND B.callnumber=L.callNumber AND L.outDate <"
				+ overDateS);
		while (rs.next()) {
			OverdueDto dto = new OverdueDto();
			dto.setCallNumber(rs.getInt("callNumber"));
			dto.setBorid(rs.getInt("borid"));
			dto.setName(rs.getString("name"));
			dto.setType(rs.getString("btype"));
			dto.setTitle(rs.getString("title"));
			dto.setEmail(rs.getString("emailAddress"));
			queryResults.add(dto);
		}

		// Staff with overdue
		overDate = DaoUtility.makeDate(-12 * DaoUtility.WEEK);
		overDateS = DaoUtility.convertToSQLvalue(overDate);
		rs = st.executeQuery("SELECT B.callNumber, L.borid, U.name, U.btype, B.title, U.emailAddress "
				+ "FROM Book B, Borrower U, Borrowing L "
				+ "WHERE U.btype='staff' AND U.bid=L.bid AND B.callnumber=L.callNumber AND L.outDate <"
				+ overDateS);
		DaoUtility.printResults(rs, 6);
		while (rs.next()) {
			OverdueDto dto = new OverdueDto();
			dto.setCallNumber(rs.getInt("callNumber"));
			dto.setBorid(rs.getInt("borid"));
			dto.setName(rs.getString("name"));
			dto.setType(rs.getString("btype"));
			dto.setTitle(rs.getString("title"));
			dto.setEmail(rs.getString("emailAddress"));
			queryResults.add(dto);
		}
		if (conn != null)
			conn.close();
		return queryResults;
	}

	/*
	 * Check-out items borrowed by a borrower. To borrow items, borrowers
	 * provide their card number and a list with the call numbers of the items
	 * they want to check out. The system determines if the borrower's account
	 * is valid and if the library items are available for borrowing. Then it
	 * registers the items as "out", adding them to the list of library
	 * materials that are on-loan by that borrower and prints a note with the
	 * item's details and the due day.
	 */

	public Date borrowItem(int bid, int callNo) throws SQLException {

		Connection conn = null;
		conn = connServ.getConnection();
		Statement st = conn.createStatement();
		ResultSet rs = null;
		PreparedStatement ps = null;
		// Check if has overdue book
		rs = st.executeQuery("SELECT amount FROM Fine F, Borrowing B "
				+ "WHERE F.amount > 0 AND B.borid=F.borid AND B.bid="
				+ DaoUtility.convertToSQLvalue(bid));
		if (rs.next()) {
			System.out.println("You currently have a fine of "
					+ rs.getInt("amount"));
//			throw new SQLException("You have a fine.");
		}

		// get an available copy
		int copy = findAvailableCopy(callNo);
		if (copy == -1) {
			// prompt user to request hold
			System.out.println("Sorry, there are no available copies ");
			throw new SQLException("No available copies ");
		}

		// Create new borrowing record
		ps = conn
				.prepareStatement("INSERT into Borrowing(bid, callNumber, copyNo, outDate) "
						+ "values (?,?,?,?)");
		ps.setInt(1, bid);
		ps.setInt(2, callNo);
		ps.setInt(3, copy);
		ps.setDate(4, DaoUtility.makeDate(0));
		
		//Update book copy to "out"
		ps.executeUpdate();
		ps = conn.prepareStatement("UPDATE BookCopy "
				+ "SET status='out' WHERE callNumber="
				+ DaoUtility.convertToSQLvalue(callNo) + " AND copyNo="
				+ DaoUtility.convertToSQLvalue(copy));
		ps.executeUpdate();
		
		rs = st.executeQuery("SELECT bookTimeLimit FROM Borrower, BorrowerType WHERE bid="+bid);
		if(!rs.next())
			throw new SQLException("Entered bid found.");
		
		return DaoUtility.makeDate(DaoUtility.calcBorrowLimit(rs.getString("bookTimeLimit")));

	}
	//Helper to find first available copy of a book.
	//returns -1 if no copies are available.
	public int findAvailableCopy(int callNum) throws SQLException {
		Connection conn = connServ.getConnection();
		Statement st = conn.createStatement();
		ResultSet rs = null;
		rs = st.executeQuery("SELECT copyNo FROM bookCopy "
				+ "WHERE status='in' AND callNumber="
				+ DaoUtility.convertToSQLvalue(callNum));
		if (rs.next()) {
			int i = rs.getInt(1);
			if (conn != null)
				conn.close();
			return i;
		}
		if (conn != null)
			conn.close();
		return -1;
	}
}
