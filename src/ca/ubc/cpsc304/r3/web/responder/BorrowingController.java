package ca.ubc.cpsc304.r3.web.responder;

import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ca.ubc.cpsc304.r3.db.ClerkDao;
import ca.ubc.cpsc304.r3.db.ConnectionService;
import ca.ubc.cpsc304.r3.web.DirectorServlet.ViewAndParams;

public class BorrowingController {

	public ViewAndParams checkOutBook(HttpServletRequest request) {
		Map<String, String[]> reqParams = request.getParameterMap();
		ClerkDao clerk = new ClerkDao(ConnectionService.getInstance());
//		clerk = new ClerkDao();
//		clerk.startConnection();
		String[] books = listStrings(reqParams.get("callNumber")[0]);
		for (int i = 0; i < books.length; i++) {
			
			System.out.println(Integer.parseInt(reqParams.get("bid")[0])
					+ " " + Integer.parseInt(books[i]));
			try {
				clerk.borrowItem(Integer.parseInt(reqParams.get("bid")[0]),
						 Integer.parseInt(books[i]));
			} catch (NumberFormatException e) {
				System.out.println("NOT A NUMBER!");
				return new ViewAndParams("/jsp/error.jsp");
			} catch (Exception e) {
				System.err.println(e);
//				e.printStackTrace();
				return new ViewAndParams("/jsp/error.jsp");
			}
		}
		ViewAndParams vp = new ViewAndParams("/jsp/clerk/checkOutResults.jsp");
		vp.putViewParam("bookOut", reqParams);
		return vp;
	}

	public ViewAndParams checkOutBookForm() {
		ViewAndParams vp = new ViewAndParams("/jsp/clerk/checkOutForm.jsp");
		return vp;
	}

	public ViewAndParams processReturnResults(HttpServletRequest request) {
		ViewAndParams vp = new ViewAndParams(
				"/jsp/clerk/processReturnResults.jsp");
		return vp;
	}

	public ViewAndParams processReturnForm() {
		ViewAndParams vp = new ViewAndParams("/jsp/clerk/processReturnForm.jsp");
		return vp;
	}

	// helper functions to convert string input into list of book call numbers
	private String[] listStrings(String s) {
		if (s == null)
			return null;
		int count = countChars(s, ',', 0);
		String[] sList = new String[count + 1];
		return listStringsHelper(s, count, sList, 0);
	}

	private String[] listStringsHelper(String s, int n, String[] list, int index) {
		if (n == 0 || s.length() - 1 == index) {
			list[list.length - 1] = s.substring(0);
			return list;
		}
		if (s.charAt(index) == ',') {
			list[list.length - 1 - n] = s.substring(0, index);
			return listStringsHelper(s.substring(index + 1).trim(), n - 1, list, 0);
		}
		return listStringsHelper(s, n, list, index + 1);
	}

	private int countChars(String s, char c, int acc) {
		int count = 0;
		for (int i = 0; i < s.length(); i++)
			if (s.charAt(i) == c)
				count++;
		return count;
	}
}
