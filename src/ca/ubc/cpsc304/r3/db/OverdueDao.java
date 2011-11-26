package ca.ubc.cpsc304.r3.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ca.ubc.cpsc304.r3.DaoUtility;
import ca.ubc.cpsc304.r3.dto.OverdueDto;

public class OverdueDao {
	private ConnectionService connServ;
	
	public OverdueDao(ConnectionService instance) {
		connServ=instance;
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

}
