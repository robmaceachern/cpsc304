package ca.ubc.cpsc304.r3.web.responder;

import javax.servlet.http.HttpServletRequest;

import ca.ubc.cpsc304.r3.web.DirectorServlet.ViewAndParams;

public class ReportController {

	public ViewAndParams getCheckedOutBooksForm() {
		ViewAndParams vp = new ViewAndParams("/jsp/librarian/reportCheckedOutBooksForm.jsp");
		return vp;
	}

	public ViewAndParams getCheckedOutBooksReport(HttpServletRequest request) {
		ViewAndParams vp = new ViewAndParams("/jsp/librarian/reportCheckedOutBooksDisplay.jsp");
		return vp;
	}

	public ViewAndParams getMostPopularBooksReportForm() {
		ViewAndParams vp = new ViewAndParams("/jsp/librarian/reportMostPopularBooksForm.jsp");
		return vp;
	}

	public ViewAndParams getMostPopularBooksReport(HttpServletRequest request) {
		ViewAndParams vp = new ViewAndParams("/jsp/librarian/reportMostPopularBooksDisplay.jsp");
		return vp;
	}
	
}
