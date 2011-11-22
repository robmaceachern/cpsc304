package ca.ubc.cpsc304.r3.web.responder;

import javax.servlet.http.HttpServletRequest;

import ca.ubc.cpsc304.r3.web.DirectorServlet.ViewAndParams;

public class BorrowerController {

	public ViewAndParams removeBorrower(HttpServletRequest request) {
		ViewAndParams vp = new ViewAndParams("/jsp/librarian/removeBorrowerResults.jsp");
		return vp;
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
