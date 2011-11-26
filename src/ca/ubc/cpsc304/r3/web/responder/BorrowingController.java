package ca.ubc.cpsc304.r3.web.responder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ca.ubc.cpsc304.r3.DNEException;
import ca.ubc.cpsc304.r3.db.BookDao;
import ca.ubc.cpsc304.r3.db.BorrowerDao;
import ca.ubc.cpsc304.r3.db.ConnectionService;
import ca.ubc.cpsc304.r3.db.ReturnDao;
import ca.ubc.cpsc304.r3.dto.BookCopyDto;
import ca.ubc.cpsc304.r3.dto.ReturnDto;
import ca.ubc.cpsc304.r3.web.DirectorServlet.ViewAndParams;

public class BorrowingController {
	private String numErrorMsg = "Please make sure you enter valid numbers.";

	public ViewAndParams checkOutBookResults(HttpServletRequest request) {
		ViewAndParams vp = new ViewAndParams("/jsp/clerk/checkOutResults.jsp");
		Map<String, String[]> reqParams = request.getParameterMap();
		BorrowerDao clerk = new BorrowerDao(ConnectionService.getInstance());
		ArrayList<String> books = listStrings(reqParams.get("callNumber")[0]);
		ArrayList<String> bookNames = new ArrayList<String>();
		BookDao bdao = new BookDao(ConnectionService.getInstance());
		java.sql.Date duedate = new java.sql.Date(0);
		String error = "";
		boolean hasError = false;
		int i;
		for (i = 0; i < books.size(); i++) {
			System.out.println(Integer.parseInt(reqParams.get("bid")[0]) + " "
					+ Integer.parseInt(books.get(i)));
			try {
				duedate = clerk.borrowItem(
						Integer.parseInt(reqParams.get("bid")[0]),
						Integer.parseInt(books.get(i)));
				bookNames.add(bdao
						.getByCallNumber(Integer.parseInt(books.get(i))).get(0)
						.getTitle());
			} catch (NumberFormatException e) {
				System.out.println("NOT A NUMBER!");
				hasError = true;
				vp.putViewParam("errorMsg", numErrorMsg);
			} catch (Exception e) {
				hasError = true;
				error = error + e.getMessage() + " for call number "
						+ books.get(i) + ".<br>";
				vp.putViewParam("errorMsg", error);
				// e.printStackTrace();
			}
		}
		vp.putViewParam("hasError", hasError);
		vp.putViewParam("booksOut", bookNames);
		vp.putViewParam("duedate", duedate);

		return vp;

	}

	public ViewAndParams checkOutBookForm() {
		ViewAndParams vp = new ViewAndParams("/jsp/clerk/checkOutForm.jsp");
		return vp;
	}

	public ViewAndParams processReturnResults(HttpServletRequest request) {
		ViewAndParams vp = new ViewAndParams(
				"/jsp/clerk/processReturnResults.jsp");
		Map<String, String[]> reqParams = request.getParameterMap();
		ReturnDao rdao = new ReturnDao(ConnectionService.getInstance());
		BookCopyDto bcd = new BookCopyDto();
		boolean hasError = false;
		boolean DNEerror = false;
		try {
			bcd.setCallNumber(Integer.valueOf(reqParams.get("callNumber")[0]));
			bcd.setCopyNo(Integer.valueOf(reqParams.get("copyNo")[0]));
			ReturnDto rdto = rdao.processReturn(bcd);

			// Notify requester if on hold
			if (rdto.isOnHold()) {
				String name = rdto.getName();
				String email = rdto.getEmail();
				// TODO message that email has been sent!
				System.out.println("Sending email to " + name + " at " + email
						+ " to notify of requested book.");
			}

			// Display fine if overdue
			int fine = rdto.getFine();
			if (fine > 0) {

				System.out
						.println("This is overdue! To check out any more books you must pay a fine of $"
								+ fine);
				// TODO display fine message
			}

		} catch (NumberFormatException nfe) {
			System.out.println("Please ensure your input in a valid number");
			hasError = true;
			vp.putViewParam("errorMsg", numErrorMsg);
		} catch (DNEException dne) {
			System.out.println(dne.getMessage());
			DNEerror = true;
			vp.putViewParam("errorMsg", dne.getMessage());
		} catch (SQLException e) {
			hasError = true;
			vp.putViewParam("errorMsg", "Caught a SQLException");
		} catch (Exception e) {
			hasError = true;
			vp.putViewParam("errorMsg", e.getStackTrace());
		}

		vp.putViewParam("hasError", hasError);
		vp.putViewParam("DNEerror", DNEerror);
		vp.putViewParam("returns", reqParams);
		return vp;
	}

	public ViewAndParams processReturnForm() {
		ViewAndParams vp = new ViewAndParams("/jsp/clerk/processReturnForm.jsp");
		return vp;
	}

	// helper functions to convert string input into list of book call numbers
	private ArrayList<String> listStrings(String s) {
		if (s == null)
			return null;
		int count = countChars(s, ',', 0);
		ArrayList<String> sList = new ArrayList<String>();
		return listStringsHelper(s, count, sList, 0);
	}

	private ArrayList<String> listStringsHelper(String s, int n,
			ArrayList<String> list, int index) {
		if (n == 0 || s.length() - 1 == index) {
			list.add(s.substring(0));
			return list;
		}
		if (s.charAt(index) == ',') {
			list.add(s.substring(0, index));
			return listStringsHelper(s.substring(index + 1).trim(), n - 1,
					list, 0);
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
