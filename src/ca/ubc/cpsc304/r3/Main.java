package ca.ubc.cpsc304.r3;

import ca.ubc.cpsc304.r3.db.ClerkDao;
import ca.ubc.cpsc304.r3.db.ConnectionService;

public class Main {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		ClerkDao dao = new ClerkDao(ConnectionService.getInstance());
		//dao.startConnection();
		dao.checkOverdue();

	}

}
