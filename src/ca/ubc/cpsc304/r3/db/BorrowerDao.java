package ca.ubc.cpsc304.r3.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ca.ubc.cpsc304.r3.DNEException;
import ca.ubc.cpsc304.r3.DaoUtility;
import ca.ubc.cpsc304.r3.dto.BorrowerDto;

public class BorrowerDao {
	private ConnectionService connService;

	public BorrowerDao() {
	}

	public BorrowerDao(ConnectionService cs) {
		connService = cs;
	}

	/**
	 * Deletes a borrower, given a borrowerId
	 * 
	 * @param borrowerId
	 *            the id of the borrower
	 * @return the number of borrowers deleted
	 * @throws SQLException
	 *             if there was a db error during the operation
	 */
	public int removeBorrower(int borrowerId) throws SQLException {

		Connection conn = null;
		try {

			conn = this.connService.getConnection();
			PreparedStatement ps = conn
					.prepareStatement("DELETE FROM borrower " + "WHERE bid=?");
			ps.setInt(1, borrowerId);
			return ps.executeUpdate();

		} finally {

			if (conn != null) {
				conn.close();
			}
		}
	}

	/**
	 *  Add a new borrower to the system.
	 * @param bd info about borrower to be added
	 * @return bid of added borrower
	 * @throws Exception: DNEException if unable to find newly added borrower
	 * 					  or SQLException if error inserting borrower
	 */
	public int addBorrower(BorrowerDto bd) throws Exception {
		Connection conn = null;
		try {
			conn = connService.getConnection();
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

			ResultSet rs = conn.createStatement().executeQuery(
					"SELECT bid FROM Borrower WHERE sinOrStNo="
							+ DaoUtility.convertToSQLvalue(bd.getSin()));
			if(!rs.next())
				throw new DNEException("BID not found for borrower!");
			
			return rs.getInt("bid");
		} finally {
			if (conn != null)

				conn.close();
		}
	}
	
	public boolean checkValidBorid(int id) throws SQLException{
		Connection conn = null; 
		try {			
			conn = connService.getConnection();	
			PreparedStatement ps = conn.prepareStatement(
					"SELECT * "+
					"FROM borrower "  +
					"WHERE bid=?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			// get the number of rows in the result set
			rs.last();
			int num = rs.getRow();
			
			// should be 1 if this is a valid borrower ID
			if(num==1){
				return true;
			}
			else{
				return false;
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
	}

}
