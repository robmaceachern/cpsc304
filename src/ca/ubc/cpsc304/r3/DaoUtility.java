package ca.ubc.cpsc304.r3;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import ca.ubc.cpsc304.r3.db.ConnectionService;

public class DaoUtility {

	public static ConnectionService connServ = ConnectionService.getInstance();
	// offset for 1 year
	public static final long YEAR = 1000 * 60 * 60 * 24 * 356L;
	// offset for 1 week
	public static final long WEEK = 1000 * 60 * 60 * 24 * 7L;
	
	// HELPER FUNCTIONS
	// Probably should be put into some DaoUtility class.

	// Converts value into SQL readable value.
	public static String convertToSQLvalue(Object o) {
		return "'" + o.toString() + "'";
	}


	// Calculates the borrowing limit for given limit value.
	public static long calcBorrowLimit(String typeLimit) {
		long limit = 0;
		switch (typeLimit.charAt(0)) {
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
	public static Date makeDate(long time) {
		return new Date(Calendar.getInstance().getTimeInMillis() + time);
	}

	// Print the columns and their values of the result set. Specify count of
	// columns expected.
	public static void printResults(ResultSet res, int count) throws SQLException {
		while (res.next()) {
			for (int i = 1; i <= count; i++) {
				String col = res.getMetaData().getColumnName(i);
				Object val = res.getObject(i);
				System.out.println(col + ": " + val);
			}
			System.out.println();
		}
	}
}
