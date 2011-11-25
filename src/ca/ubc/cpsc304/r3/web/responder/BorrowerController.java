package ca.ubc.cpsc304.r3.web.responder;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ca.ubc.cpsc304.r3.db.ConnectionService;
import ca.ubc.cpsc304.r3.web.DirectorServlet.ViewAndParams;

public class BorrowerController {

	/**
	 * From requirements:
	 * Remove a borrower from the library. The borrow id is provided.
	 * 
	 * @param request the http request
	 * @return the removeBorrowerResults view and the associated parameters
	 */
	public ViewAndParams removeBorrower(HttpServletRequest request) {
		
		ViewAndParams vp = new ViewAndParams("/jsp/librarian/removeBorrowerResults.jsp");
		try {
			
			@SuppressWarnings("unchecked")
			Map<String, String[]> reqParams = request.getParameterMap();
			int borrowerId = Integer.parseInt(reqParams.get("borrowerId")[0]);
			
			BorrowerDao dao = new BorrowerDao(ConnectionService.getInstance());
			int numBorrowersRemoved = dao.removeBorrower(borrowerId);
			vp.putViewParam("numBorrowersRemoved", numBorrowersRemoved);
			vp.putViewParam("borrowerId", borrowerId);
			return vp;
			
		} catch (Exception e) {
			
			vp.putViewParam("hasError", true);
			vp.putViewParam("errorMsg", BookController.generateFriendlyError(e));
			return vp;
		}
		
	}

	public ViewAndParams getRemoveBorrowerForm() {
		ViewAndParams vp = new ViewAndParams("/jsp/librarian/removeBorrowerForm.jsp");
		return vp;
	}
	
	public ViewAndParams addNewBorrower(HttpServletRequest request){
		ViewAndParams vp = new ViewAndParams("/jsp/clerk/addNewBorrowerResults.jsp");
		return vp;
	}
	
	public ViewAndParams getNewBorrowerForm(){
		ViewAndParams vp = new ViewAndParams("/jsp/clerk/addNewBorrowerForm.jsp");
		return vp;
	}

}
