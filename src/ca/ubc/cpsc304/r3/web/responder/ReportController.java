package ca.ubc.cpsc304.r3.web.responder;


import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ca.ubc.cpsc304.r3.db.BorrowingDao;
import ca.ubc.cpsc304.r3.db.ClerkDao;
import ca.ubc.cpsc304.r3.db.ConnectionService;
import ca.ubc.cpsc304.r3.dto.CheckedOutBookDto;
import ca.ubc.cpsc304.r3.dto.OverdueDto;
import ca.ubc.cpsc304.r3.web.DirectorServlet.ViewAndParams;

public class ReportController {

	public ViewAndParams getCheckedOutBooksForm() {
		ViewAndParams vp = new ViewAndParams("/jsp/librarian/reportCheckedOutBooksForm.jsp");
		return vp;
	}

	/**
	 * From requirements:
	 * 
	 * Generate a report with all the books that have been checked out.
	 * For each book the report shows the date it was checked out and the
	 * due date. The system flags the items that are overdue. The items are
	 * ordered by the book call number.  If a subject is provided the report
	 * lists only books related to that subject, otherwise all the books that
	 * are out are listed by the report.
	 * 
	 * 
	 */
	public ViewAndParams getCheckedOutBooksReport(HttpServletRequest request) {
		ViewAndParams vp = new ViewAndParams("/jsp/librarian/reportCheckedOutBooksDisplay.jsp");
		try{
			
			@SuppressWarnings("unchecked")
			Map<String, String[]> reqParams = request.getParameterMap();
			
			String subject = reqParams.get("subject")[0];
			BorrowingDao dao = new BorrowingDao(ConnectionService.getInstance());
			List<CheckedOutBookDto> checkedOutBooks = dao.generateCheckedOutBooksReport(subject);
			
			vp.putViewParam("checkedOutBooks", checkedOutBooks);
			vp.putViewParam("now", new Date());
			
			return vp;
			
		} catch (Exception e){
			
			vp.putViewParam("hasError", true);
			vp.putViewParam("errorMsg", BookController.generateFriendlyError(e));
			return vp;
			
		}


	}

	public ViewAndParams getMostPopularBooksReportForm() {
		ViewAndParams vp = new ViewAndParams("/jsp/librarian/reportMostPopularBooksForm.jsp");
		return vp;
	}

	/**
	 * 
	 * Generate a report with the most popular items
	 * in a given year.  The librarian provides a year
	 * and a number n. The system lists out the top n
	 * books that where borrowed the most times during
	 * that year. The books are ordered by the number
	 * of times they were borrowed.
	 * 
	 */
	public ViewAndParams getMostPopularBooksReport(HttpServletRequest request) {
		ViewAndParams vp = new ViewAndParams("/jsp/librarian/reportMostPopularBooksDisplay.jsp");
		

		
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
		Map<String, String[]> reqParams = new HashMap<String, String[]>();
		ClerkDao dao = new ClerkDao(ConnectionService.getInstance());
		try {
			List<OverdueDto> dtos = dao.checkOverdue();
			int size = dtos.size();
			String[] titles = new String[size];
			String[] borrowers = new String[size];
			String[] emails = new String[size];
			
			for(int i=0; i<size; i++){
				titles[i] = dtos.get(i).getTitle();
				borrowers[i] = dtos.get(i).getName();
				emails[i] = dtos.get(i).getEmail();
			}
			
			reqParams.put("Title", titles);
			reqParams.put("Name", borrowers);
			reqParams.put("Email", emails);
			
			
		} catch (SQLException e) {
			// TODO UNABLE TO GET OVERDUE REPORT
			e.printStackTrace();
		}
		

		ViewAndParams vp = new ViewAndParams("/jsp/clerk/checkOverdueDisplay.jsp");
		vp.putViewParam("overdue", reqParams);
		return vp;
	}
	
	
}
