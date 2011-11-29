package ca.ubc.cpsc304.r3.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ca.ubc.cpsc304.r3.dto.OverdueDto;
import ca.ubc.cpsc304.r3.util.DaoUtility;

public class OverdueDao {
	private ConnectionService connServ;

	public OverdueDao(ConnectionService instance) {
		connServ = instance;
	}

	/**
	 * Checks for all overdue items If user provides input id then that only
	 * overdue items for that user will be shown.
	 * 
	 * @param bid
	 *            bid of borrower
	 * @return List of overdue items
	 * @throws SQLException
	 *             excetion thrown if errors arise in query
	 */

	public List<OverdueDto> checkOverdue(String bid) throws SQLException {

		List<OverdueDto> queryResults = new ArrayList<OverdueDto>();
		Connection conn = null;
		try {
			conn = connServ.getConnection();
			PreparedStatement ps = null;
			ResultSet rs = null;
			long borLim = 0;

			// If bid is input
			String checkBid = bid;
			if (!bid.equals("")) {
				checkBid = " AND U.bid="
						+ DaoUtility.convertToSQLvalue(Integer.parseInt(bid));

				// Check for valid borrower id.
				ps = conn
						.prepareStatement("SELECT COUNT(*) FROM Borrower WHERE bid=?");
				ps.setString(1, bid);
				rs = ps.executeQuery();
				if (!rs.next() || rs.getInt(1) <= 0)
					throw new SQLException();
			}
			
			// Students with overdue
			Date overDate = DaoUtility.makeDate(-2 * DaoUtility.WEEK);
			borLim = 2 * DaoUtility.WEEK;
			overDate = DaoUtility.makeDate(-1 * borLim);
			ps = conn
					.prepareStatement("SELECT L.outDate AS dueDate, B.callNumber, L.borid, U.name, U.btype, B.title, U.emailAddress "
							+ "FROM Book B, Borrower U, Borrowing L "
							+ "WHERE U.btype='student' AND U.bid=L.bid AND "
							+ "B.callnumber=L.callNumber AND L.inDate IS NULL AND L.outDate <? "
							+ checkBid);
			ps.setDate(1, overDate);
			rs = ps.executeQuery();
			while (rs.next()) {
				OverdueDto dto = new OverdueDto();
				dto.setCallNumber(rs.getInt("callNumber"));
				dto.setBorid(rs.getInt("borid"));
				dto.setName(rs.getString("name"));
				dto.setType(rs.getString("btype"));
				dto.setTitle(rs.getString("title"));
				dto.setEmail(rs.getString("emailAddress"));
				dto.setDuedate(new Date(rs.getDate("dueDate").getTime()
						+ borLim));
				queryResults.add(dto);
			}

			// Faculty with overdue
			borLim = 12 * DaoUtility.WEEK;
			overDate = DaoUtility.makeDate(-1 * borLim);
			ps = conn
					.prepareStatement("SELECT L.outDate AS dueDate, B.callNumber, L.borid, U.name, U.btype, B.title, U.emailAddress "
							+ "FROM Book B, Borrower U, Borrowing L "
							+ "WHERE U.btype='faculty' AND U.bid=L.bid AND "
							+ "B.callnumber=L.callNumber AND L.inDate IS NULL AND L.outDate <?"
							+ checkBid);
			ps.setDate(1, overDate);
			rs = ps.executeQuery();
			while (rs.next()) {
				OverdueDto dto = new OverdueDto();
				dto.setCallNumber(rs.getInt("callNumber"));
				dto.setBorid(rs.getInt("borid"));
				dto.setName(rs.getString("name"));
				dto.setType(rs.getString("btype"));
				dto.setTitle(rs.getString("title"));
				dto.setEmail(rs.getString("emailAddress"));
				dto.setDuedate(new Date(rs.getDate("dueDate").getTime()
						+ borLim));
				queryResults.add(dto);
			}

			// Staff with overdue
			borLim = 6 * DaoUtility.WEEK;
			overDate = DaoUtility.makeDate(-1 * borLim);
			ps = conn
					.prepareStatement("SELECT L.outDate AS dueDate, B.callNumber, L.borid, U.name, U.btype, B.title, U.emailAddress "
							+ "FROM Book B, Borrower U, Borrowing L "
							+ "WHERE U.btype='staff' AND U.bid=L.bid AND "
							+ "B.callnumber=L.callNumber AND L.inDate IS NULL AND L.outDate <?"
							+ checkBid);
			ps.setDate(1, overDate);
			rs = ps.executeQuery();
			while (rs.next()) {
				OverdueDto dto = new OverdueDto();
				dto.setCallNumber(rs.getInt("callNumber"));
				dto.setBorid(rs.getInt("borid"));
				dto.setName(rs.getString("name"));
				dto.setType(rs.getString("btype"));
				dto.setTitle(rs.getString("title"));
				dto.setEmail(rs.getString("emailAddress"));
				dto.setDuedate(new Date(rs.getDate("dueDate").getTime()
						+ borLim));
				queryResults.add(dto);
			}
		} finally {
			if (conn != null)
				conn.close();
		}
		return queryResults;
	}

}
