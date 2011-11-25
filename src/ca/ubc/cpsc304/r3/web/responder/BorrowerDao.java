package ca.ubc.cpsc304.r3.web.responder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import ca.ubc.cpsc304.r3.db.ConnectionService;

/**
 * The data access class for the Borrower table
 * @author Rob
 *
 */
public class BorrowerDao {
	
	private ConnectionService connService;
	
	public BorrowerDao(ConnectionService connService){
		this.connService = connService;
	}

	/**
	 * Deletes a borrower, given a borrowerId
	 * @param borrowerId the id of the borrower
	 * @return the number of borrowers deleted
	 * @throws SQLException if there was a db error during the operation
	 */
	public int removeBorrower(int borrowerId) throws SQLException {
		
		Connection conn = null;
		try {
			
			conn = this.connService.getConnection();
			PreparedStatement ps = conn.prepareStatement(
					"DELETE FROM borrower " +
					"WHERE bid=?");
			ps.setInt(1, borrowerId);
			return ps.executeUpdate();
			
		} finally {
			
			if(conn != null){
				conn.close();
			}
		}
	}
	
	

}
