package ca.ubc.cpsc304.r3;

public class Main {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		ClerkDao dao = new ClerkDao();
		dao.startConnection();
		dao.checkOverdue();

	}

}
