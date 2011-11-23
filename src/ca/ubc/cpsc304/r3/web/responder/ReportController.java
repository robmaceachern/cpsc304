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
		/*
		 * Generate a report with all the books that have been checked out.
		 * For each book the report shows the date it was checked out and the
		 * due date. The system flags the items that are overdue. The items are
		 * ordered by the book call number.  If a subject is provided the report
		 * lists only books related to that subject, otherwise all the books that
		 * are out are listed by the report.
		 */
		
		// filter by subject
		// order by call number
		return vp;
	}

	public ViewAndParams getMostPopularBooksReportForm() {
		ViewAndParams vp = new ViewAndParams("/jsp/librarian/reportMostPopularBooksForm.jsp");
		return vp;
	}

	public ViewAndParams getMostPopularBooksReport(HttpServletRequest request) {
		ViewAndParams vp = new ViewAndParams("/jsp/librarian/reportMostPopularBooksDisplay.jsp");
		
		/*
		 * Generate a report with the most popular items
		 * in a given year.  The librarian provides a year
		 * and a number n. The system lists out the top n
		 * books that where borrowed the most times during
		 * that year. The books are ordered by the number
		 * of times they were borrowed.
		 */
		
		// filter by year and limit by n
		// order by number of times borrowed
		// list callNumber, book title, mainAuthor, and number of times borrowed
		
		return vp;
	}

	public ViewAndParams getOverdueReportForm() {
		ViewAndParams vp = new ViewAndParams("/jsp/clerk/checkOverdueForm.jsp");
		return vp;
	}

	public ViewAndParams getOverdueReport(HttpServletRequest request) {
		ViewAndParams vp = new ViewAndParams("/jsp/clerk/checkOverdueDisplay.jsp");
		return vp;
	}
	
	
}
