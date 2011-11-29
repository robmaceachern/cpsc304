package ca.ubc.cpsc304.r3.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import ca.ubc.cpsc304.r3.DNEException;
import ca.ubc.cpsc304.r3.DaoUtility;
import ca.ubc.cpsc304.r3.dto.BookCopyDto;
import ca.ubc.cpsc304.r3.dto.ReturnDto;

public class ReturnDao {
	private ConnectionService connServ;

	public ReturnDao(ConnectionService c) {
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
		ResultSet rs = null;
		PreparedStatement ps = null;
		int callNo = bcd.getCallNumber();
		int copyNo = bcd.getCopyNo();
		ReturnDto rdto = new ReturnDto();

		// Check to see if it was checked out
		ps = conn.prepareStatement("SELECT status FROM BookCopy "
				+ "WHERE callNumber=? AND copyNo=?");
		ps.setInt(1, callNo);
		ps.setInt(2, copyNo);
		rs = ps.executeQuery();

		if (rs.next() && rs.getString(1).equals("out")) {
			// ASSUMING hold requests are deleted after borrower has checked out
			// book
			ps = conn
					.prepareStatement("SELECT name, emailAddress, hid FROM Borrower B, HoldRequest H"
							+ " WHERE H.bid=B.bid AND H.callNumber=? AND H.issuedDate < ALL "
							+ "(SELECT issuedDate FROM HoldRequest H2 "
							+ "WHERE H.hid<> H2.hid AND H2.callNumber=?)");
			ps.setInt(1, callNo);
			ps.setInt(2, callNo);
			rs = ps.executeQuery();

			// check if there is a request for this book
			if (rs.next()) {
				String name, email;
				int hid;
				name = rs.getString("name");
				email = rs.getString("emailAddress");
				hid = rs.getInt("hid");

				rdto.setRequestor(name);
				rdto.setEmail(email);
				rdto.setHold(true);
				rdto.setHoldID(hid);

				// update this copy to be on hold
				ps = conn
						.prepareStatement("UPDATE BookCopy set status='on hold' WHERE callNumber=? AND copyNo=?");
				ps.setInt(1, callNo);
				ps.setInt(2, copyNo);
				ps.executeUpdate();
			}

			// otherwise there is no request.
			else {
				// update this copy to be in
				ps = conn.prepareStatement("UPDATE BookCopy set status='in' "
						+ "WHERE callNumber=? AND copyNo=?");
				ps.setInt(1, callNo);
				ps.setInt(2, copyNo);
				ps.executeUpdate();
			}

			// Check if was overdue
			ps = conn
					.prepareStatement("SELECT bid, borid, outDate FROM Borrowing WHERE callNumber=? AND copyNo=? AND inDate IS NULL");
			ps.setInt(1, callNo);
			ps.setInt(2, copyNo);
			rs = ps.executeQuery();

			// Should never happen
			if (!rs.next())
				throw new SQLException(
						"SOMETHING IS HORRIBLY WRONG returning books");

			int bid = rs.getInt("bid");
			int borid = rs.getInt("borid");
			Date out = rs.getDate("outDate");
			ps = conn
					.prepareStatement("SELECT bookTimeLimit, B.name FROM Borrower B, BorrowerType T WHERE B.btype=T.btype AND bid=?");
			ps.setInt(1, bid);
			rs = ps.executeQuery();

			// Should never happen
			if (!rs.next())
				throw new SQLException("SOMETHING IS WORNG returning books!");

			rdto.setReturner(rs.getString("name"));

			long limit = DaoUtility.calcBorrowLimit(rs.getString(1));

			// Time overdue in ms
			long diff = Calendar.getInstance().getTimeInMillis()
					- out.getTime();
			int fine = (int) (diff / 1000 / 60 / 60 / 24);
			if (diff > limit) {
				ps = conn
						.prepareStatement("INSERT into Fine(amount, issuedDate, borid)"
								+ "values(?,?,?)");
				ps.setInt(1, fine);
				ps.setDate(2, DaoUtility.makeDate(0));
				ps.setInt(3, borid);
				ps.executeUpdate();

				rdto.setFine(fine);
			}
			ps = conn
					.prepareStatement("UPDATE Borrowing SET inDate=? WHERE borid=?");
			ps.setDate(1, DaoUtility.makeDate(0));
			ps.setInt(2, borid);
			ps.executeUpdate();

			return rdto;
		}
		// If the entered item info is not checked out
		throw new DNEException(
				"This book is not currently checked out or does not exist.");

	}
}
