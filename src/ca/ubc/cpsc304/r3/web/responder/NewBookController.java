package ca.ubc.cpsc304.r3.web.responder;

import ca.ubc.cpsc304.r3.web.DirectorServlet.ViewAndParams;

public class NewBookController {
	
	public ViewAndParams getNewBookForm(){
		ViewAndParams vp = new ViewAndParams("/jsp/librarian/addNewBook.jsp");
		return vp;
	}

}
