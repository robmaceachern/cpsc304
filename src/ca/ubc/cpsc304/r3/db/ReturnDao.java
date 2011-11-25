package ca.ubc.cpsc304.r3.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import ca.ubc.cpsc304.r3.DNEException;
import ca.ubc.cpsc304.r3.DaoUtility;
import ca.ubc.cpsc304.r3.dto.BookCopyDto;
import ca.ubc.cpsc304.r3.dto.ReturnDto;

public class ReturnDao {
	private ConnectionService connServ;
	
	public ReturnDao(ConnectionService c){
		connServ = c;
	}
	
	/*
	 * Processes a return. When an item is returned, the clerk records the
	 * return by providing the item's catalogue number. The system determines
	 * the borrower who had borrowed the item, registers the item as "in", and
	 * removes it from the list of library materials on loan to that borrower.
	 * If the item is overdue, a fine is assessed for the borrower. If there is
	 * a hold request for this item by another borrower, the item is registered
	 * as "on hold" and a message is send to the borrower who made the hold
	 * request.
	 */
	public ReturnDto processReturn(BookCopyDto bcd) throws Exception {

		Connection conn = null;
		conn = connServ.getConnection();
		Statement st = conn.createStatement();
		ResultSet rs = null;
		PreparedStatement ps = null;
		int callNo = bcd.getCallNumber();
		int copyNo = bcd.getCopyNo();
		
		ReturnDto rdto = new ReturnDto();

		String callString = DaoUtility.convertToSQLvalue(callNo);
		String copyString = DaoUtility.convertToSQLvalue(copyNo);

		// Check to see if it was checked out
		rs = st.executeQuery("SELECT status FROM BookCopy "
				+ "WHERE callNumber=" + callString + " AND copyNo="
				+ copyString);
		if (rs.next() && rs.getString(1).equals("out")) {
			// ASSUMING hold requests are deleted after they have been
			// fulfilled
			rs = st.executeQuery("SELECT name, emailAddress, hid FROM Borrower B, HoldRequest H"
					+ " WHERE H.bid=B.bid AND H.callNumber="
					+ callString
					+ " AND H.issuedDate < ALL "
					+ "(SELECT issuedDate FROM HoldRequest H2 "
					+ "WHERE H.hid<> H2.hid AND H2.callNumber="
					+ callString
					+ ")");

			// check if there is a request for this book
			if (rs.next()) {
				String name, email;
				int hid;
				name = rs.getString("name");
				email = rs.getString("emailAddress");
				hid = rs.getInt("hid");
				
				rdto.setName(name);
				rdto.setEmail(email);
				rdto.setHold(true);
				rdto.setHoldID(hid); //??
				
				//TODO display EMAIL SENT

				
				//update this copy to be on hold
				ps = conn
						.prepareStatement("UPDATE BookCopy set status='on hold' WHERE callNumber="
								+ callString +" AND copyNo=" + copyString);
				ps.executeUpdate();
				ps = conn.prepareStatement("DELETE FROM HoldRequest WHERE hid="
						+ DaoUtility.convertToSQLvalue(hid));
				ps.executeUpdate();
			}
			
			// otherwise there is no request.
			else {
				//update this copy to be in
				ps = conn.prepareStatement("UPDATE BookCopy set status='in' "
						+ "WHERE callNumber=" + callString + " AND copyNo="
						+ copyString);
				ps.executeUpdate();
				System.out.println("UPDATED bookCopy table");
			}

			
			rs = st.executeQuery("SELECT bid, borid, outDate FROM Borrowing WHERE callNumber="
					+ callString
					+ " AND copyNo="
					+ copyString
					+ " AND inDate IS NULL");
			
			//Should never happen
			if (!rs.next())
				throw new SQLException("SOMETHING IS HORRIBLY WRONG returning books");
			
				int bid = rs.getInt("bid");
				int borid = rs.getInt("borid");
				Date out = rs.getDate("outDate");
				rs = st.executeQuery("SELECT bookTimeLimit FROM Borrower B, BorrowerType T WHERE B.btype=T.btype AND bid="
						+ DaoUtility.convertToSQLvalue(bid));
				
				//Should never happen
				if(!rs.next())
					throw new SQLException("SOMETHING IS WORNG returning books!");
				
				long limit = DaoUtility.calcBorrowLimit(rs.getString(1));
				
				//Time overdue in ms
				long diff = Calendar.getInstance().getTimeInMillis()
						- out.getTime();
				int fine = (int) (diff/1000000);
				if (diff > limit) {
					ps = conn
							.prepareStatement("INSERT into Fine(amount, issuedDate, borid)"
									+ "values(" + fine + ", "
									+ DaoUtility.convertToSQLvalue(DaoUtility.makeDate(0))
									+ ", "+ borid + ")");
					ps.executeUpdate();
					
					rdto.setFine(fine);
					System.out.println("new fine");

				}
				ps = conn.prepareStatement("UPDATE Borrowing SET inDate="
						+ DaoUtility.convertToSQLvalue(DaoUtility.makeDate(0)) + " WHERE borid="
						+ borid);
				ps.executeUpdate();
			
				return rdto;
		}
		// If the entered item info is not checked out
		throw new DNEException("This book is not currently checked out or does not exist.");

	}
}
