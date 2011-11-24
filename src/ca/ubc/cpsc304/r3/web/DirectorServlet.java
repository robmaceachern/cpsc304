package ca.ubc.cpsc304.r3.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ca.ubc.cpsc304.r3.web.responder.BorrowerController;
import ca.ubc.cpsc304.r3.web.responder.BorrowingController;
import ca.ubc.cpsc304.r3.web.responder.FineController;
import ca.ubc.cpsc304.r3.web.responder.HomePageController;
import ca.ubc.cpsc304.r3.web.responder.BookController;
import ca.ubc.cpsc304.r3.web.responder.ReportController;

/**
 * This class receives all the user's web requests and then determines what
 * controller should handle it.
 */
public class DirectorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		ViewAndParams view = getViewAndParams(request);

		// this takes any parameters and makes them available to the jsp
		for (String key : view.getViewParams().keySet()) {
			request.setAttribute(key, view.getViewParams().get(key));
		}

		RequestDispatcher dispatch = getServletContext().getRequestDispatcher(
				view.getViewPath());
		dispatch.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// POST is for submitting forms
		ViewAndParams view = getPostViewAndParams(request);

		// this takes any parameters and makes them available to the jsp
		for (String key : view.getViewParams().keySet()) {
			request.setAttribute(key, view.getViewParams().get(key));
		}

		RequestDispatcher dispatch = getServletContext().getRequestDispatcher(
				view.getViewPath());
		dispatch.forward(request, response);
	}

	/**
	 * 
	 * @param request
	 *            the web request
	 * @return the ViewAndParams to display
	 */
	private ViewAndParams getViewAndParams(HttpServletRequest request) {

		// can be something like "/booklist" or "/librarian/addbook"
		String requestPath = cleanUpPath(request.getPathInfo());

		// this will grow into a giant list of if/else statements as we
		// add pages. It's not ideal, but it'll do the job.

		// home
		if (requestPath.equals("/")) {
			return new HomePageController().getHomePage(request);
		}
		// borrower
		else if(requestPath.equals("/checkaccount/")) {
			return new BorrowerController().getCheckAccountForm();
		}
		else if(requestPath.equals("/checkaccountsubmit/")){
			return new BorrowerController().getCheckAccountResults(request);
		}
		else if(requestPath.equals("/payfine/")) {
			return new FineController().getPayFineForm();
		}

		// clerk
		else if (requestPath.equals("/addnewborrower/")) {
			return new BorrowerController().getNewBorrowerForm();
		} else if (requestPath.equals("/checkoutbooks/")) {
			return new BorrowingController().checkOutBookForm();
		} else if (requestPath.equals("/processreturn/")) {
			return new BorrowingController().processReturnForm();
		} else if (requestPath.equals("/checkoverduereport/")) {
			return new ReportController().getOverdueReportForm();
		}
		// librarian
		else if (requestPath.equals("/addnewbook/")) {
			return new BookController().getNewBookForm();
		} else if (requestPath.equals("/addnewcopy/")) {
			return new BookController().getNewBookCopyForm();
		} else if (requestPath.equals("/removeborrower/")) {
			return new BorrowerController().getRemoveBorrowerForm();
		} else if (requestPath.equals("/checkedoutbooksreport/")) {
			return new ReportController().getCheckedOutBooksForm();
		} else if (requestPath.equals("/viewcheckedoutbooksreport/")) {
			return new ReportController().getCheckedOutBooksReport(request);
		} else if (requestPath.equals("/mostpopularbooksreport/")) {
			return new ReportController().getMostPopularBooksReportForm();
		} else if (requestPath.equals("/viewmostpopularbooksreport/")) {
			return new ReportController().getMostPopularBooksReport(request);
		} else if (requestPath.equals("/viewcheckoverduereport/")){
			return new ReportController().getOverdueReport(request);
		} else if (requestPath.equals("/removebook/")){
			return new BookController().getRemoveBookForm();
		}
		// an error page if nothing hits
		return new ViewAndParams("/jsp/error.jsp");
	}

	private ViewAndParams getPostViewAndParams(HttpServletRequest request) {
		String requestPath = cleanUpPath(request.getPathInfo());
		
		//borrower
		if(requestPath.equals("/payfinesubmit/")){
			return new FineController().getPayFineResults(request);
		}
		
		//clerk
		else if (requestPath.equals("/addnewborrowersubmit/")) 
			return new BorrowerController().addNewBorrower(request);
		else if (requestPath.equals("/checkoutbookssubmit/"))
			return new BorrowingController().checkOutBook(request);
		else if (requestPath.equals("/processreturnsubmit/"))
			return new BorrowingController().processReturnResults(request);
		
		//librarian
		else if (requestPath.equals("/addnewbookcopysubmit/")) {
			return new BookController().addNewBookCopy(request);
		} else if (requestPath.equals("/addnewbooksubmit/")) {
			return new BookController().addNewBook(request);
		} else if (requestPath.equals("/removeborrowersubmit/")) {
			return new BorrowerController().removeBorrower(request);
		} else if (requestPath.equals("/removebooksubmit/")){
			return new BookController().removeBook(request);
		}
		return new ViewAndParams("/jsp/error.jsp");
	}

	private String cleanUpPath(String pathInfo) {
		// treat empty or null as root
		if (pathInfo == null || pathInfo.isEmpty()) {
			return "/";
		}

		char lastChar = pathInfo.charAt(pathInfo.length() - 1);
		if (lastChar != '/') {
			return pathInfo + "/";
		}
		return pathInfo;

	}

	public static class ViewAndParams {

		private String viewPath;

		private Map<String, Object> viewParams;

		public ViewAndParams(String viewPath) {
			if (viewPath == null)
				throw new NullPointerException();
			this.viewPath = viewPath;
			this.viewParams = new HashMap<String, Object>();
		}

		public void putViewParam(String name, Object o) {
			this.viewParams.put(name, o);
		}

		public void putAllViewParams(Map<String, Object> params) {
			this.viewParams.putAll(params);
		}

		public Map<String, Object> getViewParams() {
			return new HashMap<String, Object>(this.viewParams);
		}

		public String getViewPath() {
			return this.viewPath;
		}
	}

}
