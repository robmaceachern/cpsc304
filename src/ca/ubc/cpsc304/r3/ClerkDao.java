package ca.ubc.cpsc304.r3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.sql.Date;

public class ClerkDao {

	private Connection conn = null;
	private Statement st = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	// offset for 1 year
	public final long YEAR = 1000 * 60 * 60 * 24 * 356L;
	// offset for 1 week
	public final long WEEK = 1000 * 60 * 60 * 24 * 7L;

	public void startConnection() {
		try {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			conn = DriverManager
					.getConnection("jdbc:mysql://localhost/crazycoollibrary?"
							+ "user=devuser&password=devuser");

			// Statements allow to issue SQL queries to the database
			st = conn.createStatement();
		} catch (SQLException sqlE) {
			System.out.println(sqlE.getLocalizedMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	// Add a new borrower to the system.
	public void addBorrower(String pw, String name, int phone, String address,
			String email, int sin, String type) {
		try {
			ps = conn
					.prepareStatement("INSERT into Borrower "
							+ "(password, name, phone, address, emailAddress, sinOrStNo, expiryDate, btype) "
							+ "values (?,?,?,?,?,?,?,?)");
			ps.setString(1, pw);
			ps.setString(2, name);
			ps.setInt(3, phone);
			ps.setString(4, address);
			ps.setString(5, email);
			ps.setInt(6, sin);
			ps.setDate(7, makeDate(YEAR));
			ps.setString(8, type);
			ps.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Unable to add new borrower.");
			e.printStackTrace();
		}

	}

	// Checks for all overdue items
	public void checkOverdue() {
		try {
			// Get all borrowers with books out currently
			// Need this to get each borrower's limit
			List<Integer> bids = new ArrayList<Integer>();
			List<Integer> borids = new ArrayList<Integer>();
			List<Date> dueDs = new ArrayList<Date>();
			List<Date> overDs = new ArrayList<Date>();

			// !! for some reason this query doesn't return correctly? the same
			// exact thing in heidi works as expected !!
			rs = st.executeQuery("SELECT bid, borid, outDate FROM Borrowing WHERE inDate IS NULL");
			
			//this gets all 4 i inserted into my db
//			printResults(rs, 3);

			// only ever 1 element??
			// seems related to java.sql.SQLException: Operation not allowed after ResultSet closed
			int j = 0;
			while (rs.next()) {
				int b = rs.getInt("bid");
				bids.add((Integer) b);
				borids.add((Integer) rs.getInt("borid"));
				dueDs.add(new Date(rs.getDate("outDate").getTime()
						+ getBorrowerLimit(b)));
				overDs.add(makeDate(-1 * getBorrowerLimit(b)));
				System.out.println(++j);
			}
			
			if (bids.size() < 1) {
				System.out.println("No overdue items.");
				return;
			}

			// "Email" user with overdue item
			for (int i = 0; i < bids.size(); i++) {

				String bidS = convertToSQLvalue(bids.get(i));
				String overDateS = convertToSQLvalue(overDs.get(i));
				String boridS = convertToSQLvalue(borids.get(i));

				rs = st.executeQuery("SELECT B.title, U.name, U.emailAddress "
						+ "FROM Book B, Borrower U, Borrowing L "
						+ "WHERE L.borid=" + boridS + " AND U.bid=" + bidS
						+ " AND B.callnumber=L.callNumber AND L.outDate <"
						+ overDateS);

				// printResults(rs, 3);
				while (rs.next()) {
					// Send out email
					System.out.println("Email sent to " + rs.getString("name")
							+ " at email " + rs.getString("emailAddress")
							+ " to remind them that " + rs.getString("title")
							+ " was due on " + convertToSQLvalue(dueDs.get(i))
							+ ".");

				}
				System.out.println(bids.size());
				System.out.println(i);
			}

		} catch (Exception e) {
			System.out.println("Error checking overdue.");
			e.printStackTrace();
		}
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

	public void borrowItem(int bid, int callNo) {
		try {
			// Check if has overdue book
			rs = st.executeQuery("SELECT amount FROM Fine F, Borrowing B "
					+ "WHERE F.amount > 0 AND B.borid=F.borid AND B.bid="
					+ convertToSQLvalue(bid));
			if (rs.next()) {
				System.out.println("You currently have a fine of "
						+ rs.getInt("amount"));
				return;
			}
			// get an available copy
			int copy = findAvailableCopy(callNo);
			if (copy == -1) {
				// prompt user to request hold
				System.out.println("Sorry, there are no available copies.");
				return;
			}

			// Create new borrowing record
			ps = conn
					.prepareStatement("INSERT into Borrowing(bid, callNumber, copyNo, outDate, inDate) "
							+ "values (?,?,?,?,?)");
			ps.setInt(1, bid);
			ps.setInt(2, callNo);
			ps.setInt(3, copy);
			ps.setDate(4, makeDate(0));
			ps.executeUpdate();
			printResults(rs, 6);

			ps = conn.prepareStatement("UPDATE BookCopy "
					+ "SET status='out' WHERE callNumber="
					+ convertToSQLvalue(callNo) + " AND copyNo="
					+ convertToSQLvalue(copy));
			ps.executeUpdate();
			printResults(rs, 2);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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

	public void processReturn(int callNo, int copyNo) {
		try {
			String callString = convertToSQLvalue(callNo);
			String copyString = convertToSQLvalue(copyNo);

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
						+ callString + ")");

				// if there is a request for this book notify
				if (rs.next()) {
					String name, email;
					int hid;
					name = rs.getString("name");
					email = rs.getString("emailAddress");
					hid = rs.getInt("hid");
					System.out.println("Sending email to " + name + " at "
							+ email + " to notify of requested book.");
					ps = conn
							.prepareStatement("UPDATE BookCopy set status='on hold' WHERE callNumber="
									+ callString);
					ps.executeUpdate();
					ps = conn
							.prepareStatement("DELETE FROM HoldRequest WHERE hid="
									+ convertToSQLvalue(hid));
					ps.executeUpdate();
				}
				// otherwise there is no request.
				else {
					ps = conn
							.prepareStatement("UPDATE BookCopy set status='in' "
									+ "WHERE callNumber="
									+ callString
									+ " AND copyNo=" + copyString);
					ps.executeUpdate();
					System.out.println("UPDATED bookCopy table");
				}

				rs = st.executeQuery("SELECT bid, borid, outDate FROM Borrowing WHERE callNumber="
						+ callString
						+ " AND copyNo="
						+ copyString
						+ " AND inDate IS NULL");
				if (!rs.next())
					System.out
							.println("SOMETHING IS HORRIBLY WRONG returning books");
				else {
					int bid = rs.getInt("bid");
					int borid = rs.getInt("borid");
					Date out = rs.getDate("outDate");
					rs = st.executeQuery("SELECT bookTimeLimit FROM Borrower B, BorrowerType T WHERE B.btype=T.btype AND bid="
							+ convertToSQLvalue(bid));
					rs.next();
					long limit = calcLimit(rs.getString(1));
					long diff = Calendar.getInstance().getTimeInMillis()
							- out.getTime();
					if (diff > limit) {
						ps = conn
								.prepareStatement("INSERT into Fine(amount, issuedDate, borid)"
										+ "values("
										+ diff
										/ 1000000
										+ ", "
										+ convertToSQLvalue(makeDate(0))
										+ ", "
										+ borid + ")");
						ps.executeUpdate();
						System.out.println("new fine");

					}
					ps = conn.prepareStatement("UPDATE Borrowing SET inDate="
							+ convertToSQLvalue(makeDate(0)) + " WHERE borid="
							+ borid);
					ps.executeUpdate();
				}

			}

			// If the entered item info is not checked out
			else
				System.out
						.println("This book is not currently checked out or does not exist.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Converts value into SQL readable value.
	public String convertToSQLvalue(Object o) {
		return "'" + o.toString() + "'";
	}

	public int findAvailableCopy(int callNum) {
		try {
			rs = st.executeQuery("SELECT copyNo FROM bookCopy "
					+ "WHERE status='in' AND callNumber="
					+ convertToSQLvalue(callNum));
			if (rs.next())
				return rs.getInt(1);
		} catch (SQLException e) {
			System.out.println("couldn't find a copy");
			e.printStackTrace();
		}
		return -1;
	}

	public long getBorrowerLimit(int bid) {
		try {
			rs = st.executeQuery("SELECT bookTimeLimit "
					+ "FROM BorrowerType T, Borrower U "
					+ "WHERE T.btype=U.btype AND U.bid="
					+ convertToSQLvalue(bid));
			if (rs.next())
				return calcLimit(rs.getString(1));
		} catch (Exception e) {
			System.out.println("Error retrieving user's limit");
			e.printStackTrace();
		}
		return 0;
	}

	// Calculates the borrowing limit for given limit value.
	public long calcLimit(String type) {
		long limit = 0;
		switch (type.charAt(0)) {
		case '2':
			limit = 2 * WEEK;
			break;
		case '6':
			limit = 6 * WEEK;
			break;
		default:
			limit = 12 * WEEK;
		}
		return limit;
	}

	// Make a SQL Date with indicated offset from present date.
	public Date makeDate(long time) {
		return new Date(Calendar.getInstance().getTimeInMillis() + time);
	}

	/*
	 * @SuppressWarnings("deprecation") public void readDataBase() throws
	 * Exception { try { // This will load the MySQL driver, each DB has its own
	 * driver Class.forName("com.mysql.jdbc.Driver"); // Setup the connection
	 * with the DB connect = DriverManager
	 * .getConnection("jdbc:mysql://localhost/library?" +
	 * "user=sqluser&password=sqluserpw");
	 * 
	 * // Statements allow to issue SQL queries to the database statement =
	 * connect.createStatement(); // Result set get the result of the SQL query
	 * resultSet = statement .executeQuery("select * from FEEDBACK.COMMENTS");
	 * writeResultSet(resultSet);
	 * 
	 * // PreparedStatements can use variables and are more efficient
	 * preparedStatement = connect .prepareStatement(
	 * "insert into  FEEDBACK.COMMENTS values (default, ?, ?, ?, ? , ?, ?)"); //
	 * "myuser, webpage, datum, summery, COMMENTS from FEEDBACK.COMMENTS"); //
	 * Parameters start with 1 preparedStatement.setString(1, "Test");
	 * preparedStatement.setString(2, "TestEmail");
	 * preparedStatement.setString(3, "TestWebpage");
	 * preparedStatement.setDate(4, new java.sql.Date(2009, 12, 11));
	 * preparedStatement.setString(5, "TestSummary");
	 * preparedStatement.setString(6, "TestComment");
	 * preparedStatement.executeUpdate();
	 * 
	 * preparedStatement = connect .prepareStatement(
	 * "SELECT myuser, webpage, datum, summery, COMMENTS from FEEDBACK.COMMENTS"
	 * ); resultSet = preparedStatement.executeQuery();
	 * writeResultSet(resultSet);
	 * 
	 * // Remove again the insert comment preparedStatement = connect
	 * .prepareStatement("delete from FEEDBACK.COMMENTS where myuser= ? ; ");
	 * preparedStatement.setString(1, "Test");
	 * preparedStatement.executeUpdate();
	 * 
	 * resultSet = statement .executeQuery("select * from FEEDBACK.COMMENTS");
	 * writeMetaData(resultSet);
	 * 
	 * } catch (Exception e) { throw e; } finally { close(); }
	 * 
	 * }
	 */
	private void writeMetaData(ResultSet resultSet) throws SQLException {
		// Now get some metadata from the database
		// Result set get the result of the SQL query

		System.out.println("The columns in the table are: ");

		System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
		for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
			System.out.println("Column " + i + " "
					+ resultSet.getMetaData().getColumnName(i));
		}
	}

	private void writeResultSet(ResultSet resultSet) throws SQLException {
		// ResultSet is initially before the first data set
		while (resultSet.next()) {
			// It is possible to get the columns via name
			// also possible to get the columns via the column number
			// which starts at 1
			// e.g. resultSet.getSTring(2);
			String user = resultSet.getString("myuser");
			String website = resultSet.getString("webpage");
			String summery = resultSet.getString("summery");
			Date date = resultSet.getDate("datum");
			String comment = resultSet.getString("comments");
			System.out.println("User: " + user);
			System.out.println("Website: " + website);
			System.out.println("Summery: " + summery);
			System.out.println("Date: " + date);
			System.out.println("Comment: " + comment);
		}
	}

	private void printBorrower(ResultSet resultSet) throws SQLException {
		// ResultSet is initially before the first data set
		while (resultSet.next()) {
			// It is possible to get the columns via name
			// also possible to get the columns via the column number
			// which starts at 1
			// e.g. resultSet.getSTring(2);
			String user = resultSet.getString("name");
			String address = resultSet.getString("address");
			String email = resultSet.getString("emailAddress");
			Date date = resultSet.getDate("expiryDate");
			int phone = resultSet.getInt("phone");
			int sin = resultSet.getInt("sinOrStNo");
			int bid = resultSet.getInt("bid");
			String pw = resultSet.getString("password");
			String type = resultSet.getString("btype");
			System.out.println("Name: " + user);
			System.out.println("Address: " + address);
			System.out.println("Email: " + email);
			System.out.println("Phone: " + phone);
			System.out.println("SIN: " + sin);
			System.out.println("Date: " + date);
			System.out.println("BID: " + bid);
			System.out.println("Password: " + pw);
			System.out.println("Type: " + type);
		}
	}

	// Print the columns and their values of the result set. Specify count of
	// columns expected.
	public void printResults(ResultSet res, int count) throws SQLException {
		while (res.next()) {
			for (int i = 1; i <= count; i++) {
				String col = res.getMetaData().getColumnName(i);
				Object val = res.getObject(i);
				System.out.println(col + ": " + val);
			}System.out.println();
		}
	}

	// Close connection
	private void closeConnection() {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			System.out.println("Connection did not close!");
			e.printStackTrace();

		}
	}
}
