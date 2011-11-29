package ca.ubc.cpsc304.r3.web.responder;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ca.ubc.cpsc304.r3.db.BorrowingDao;
import ca.ubc.cpsc304.r3.db.ConnectionService;
import ca.ubc.cpsc304.r3.db.ReturnDao;
import ca.ubc.cpsc304.r3.dto.BookCopyDto;
import ca.ubc.cpsc304.r3.dto.ReturnDto;
import ca.ubc.cpsc304.r3.web.DirectorServlet.ViewAndParams;

public class BorrowingController {

	public ViewAndParams checkOutBookResults(HttpServletRequest request) {
		ViewAndParams vp = new ViewAndParams("/jsp/clerk/checkOutResults.jsp");
		boolean hasError = false;
		@SuppressWarnings("unchecked")
		Map<String, String[]> reqParams = request.getParameterMap();

		try {
			BookController.checkForBadInput(reqParams);
			int bid = Integer.parseInt(reqParams.get("bid")[0]);
			String callNums = reqParams.get("callNumber")[0];
			BorrowingDao clerk = new BorrowingDao(
					ConnectionService.getInstance());
			ArrayList<String> books = listStrings(callNums);
			ArrayList<String> bookNames = new ArrayList<String>();
			java.sql.Date duedate = new java.sql.Date(0);
			for (int i = 0; i < books.size(); i++) {

				bookNames.add(clerk
						.findBookByCallNum(Integer.parseInt(books.get(i)), bid)
						.getTitle());
			}
			for (int i = 0; i < books.size(); i++) {
				duedate = clerk.borrowItem(bid, Integer.parseInt(books.get(i)));
			}
			vp.putViewParam("booksOut", bookNames);
			vp.putViewParam("duedate", duedate);
		} catch (Exception e) {
			hasError = true;
			vp.putViewParam("errorMsg", BookController.generateFriendlyError(e));
		}

		vp.putViewParam("hasError", hasError);
		return vp;

	}

	public ViewAndParams checkOutBookForm() {
		ViewAndParams vp = new ViewAndParams("/jsp/clerk/checkOutForm.jsp");
		return vp;
	}

	public ViewAndParams processReturnResults(HttpServletRequest request) {
		ViewAndParams vp = new ViewAndParams(
				"/jsp/clerk/processReturnResults.jsp");
		@SuppressWarnings("unchecked")
		Map<String, String[]> reqParams = request.getParameterMap();
		boolean hasError = false;
		// boolean DNEerror = false;

		try {
			BookController.checkForBadInput(reqParams);
			ReturnDao rdao = new ReturnDao(ConnectionService.getInstance());
			BookCopyDto bcd = new BookCopyDto();
			String copy = reqParams.get("copyNo")[0];
			String callNum = reqParams.get("callNumber")[0];
			bcd.setCallNumber(Integer.valueOf(callNum));
			bcd.setCopyNo(Integer.valueOf(copy));
			ReturnDto rdto = rdao.processReturn(bcd);
			// Notify requester if on hold
			vp.putViewParam("onHold", rdto.isOnHold());
			vp.putViewParam("returner", rdto.getReturner());
			vp.putViewParam("requesterName", rdto.getRequestor());
			vp.putViewParam("requesterEmail", rdto.getEmail());

			// Display fine if overdue
			int fine = rdto.getFine();
			if (fine > 0) {
				vp.putViewParam("fineMsg",
						"This book was overdue. You now have a fine of $"
								+ fine + " so your account has been frozen.");
			}
		} catch (Exception e) {
			hasError = true;
			vp.putViewParam("errorMsg", BookController.generateFriendlyError(e));
		}

		vp.putViewParam("hasError", hasError);
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
