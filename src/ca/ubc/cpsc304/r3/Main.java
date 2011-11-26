package ca.ubc.cpsc304.r3;

import ca.ubc.cpsc304.r3.db.BorrowerDao;
import ca.ubc.cpsc304.r3.db.ConnectionService;

public class Main {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		BorrowerDao dao = new BorrowerDao(ConnectionService.getInstance());
//		ClerkDao dao = new ClerkDao();
//		dao.startConnection();
		dao.borrowItem(1, 1);
//		dao.checkOverdue();

	}

}
