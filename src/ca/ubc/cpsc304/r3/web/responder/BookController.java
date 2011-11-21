package ca.ubc.cpsc304.r3.web.responder;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ca.ubc.cpsc304.r3.web.DirectorServlet.ViewAndParams;

public class BookController {
	
	public ViewAndParams getNewBookForm(){
		ViewAndParams vp = new ViewAndParams("/jsp/librarian/addNewBookForm.jsp");
		return vp;
	}

	public ViewAndParams addNewBook(HttpServletRequest request) {
		@SuppressWarnings("unchecked")
		Map<String, String[]> reqParams = request.getParameterMap();
		
		ViewAndParams vp = new ViewAndParams("/jsp/librarian/addNewBookResults.jsp");
		vp.putViewParam("bookAdded", reqParams);
		return vp;
	}

	public ViewAndParams getNewBookCopyForm() {
		ViewAndParams vp = new ViewAndParams("/jsp/librarian/addNewBookCopyForm.jsp");
		return vp;
	}

	public ViewAndParams addNewBookCopy(HttpServletRequest request) {
		ViewAndParams vp = new ViewAndParams("/jsp/librarian/addNewBookCopyResults.jsp");
		return vp;
	}

}
