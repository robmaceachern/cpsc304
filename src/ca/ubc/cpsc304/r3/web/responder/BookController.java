package ca.ubc.cpsc304.r3.web.responder;

import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ca.ubc.cpsc304.r3.db.BookDao;
import ca.ubc.cpsc304.r3.db.ConnectionService;
import ca.ubc.cpsc304.r3.dto.BookDto;
import ca.ubc.cpsc304.r3.web.DirectorServlet.ViewAndParams;

public class BookController {

	public ViewAndParams getNewBookForm(){
		ViewAndParams vp = new ViewAndParams("/jsp/librarian/addNewBookForm.jsp");
		return vp;
	}

	public ViewAndParams addNewBook(HttpServletRequest request) {
		ViewAndParams vp = new ViewAndParams("/jsp/librarian/addNewBookResults.jsp");

		try {
			@SuppressWarnings("unchecked")
			Map<String, String[]> reqParams = request.getParameterMap();
			
			BookDto dto = new BookDto();
			dto.setIsbn(Integer.parseInt(reqParams.get("isbn")[0]));
			dto.setMainAuthor(reqParams.get("author")[0]);
			dto.setTitle(reqParams.get("title")[0]);
			dto.setPublisher(reqParams.get("publisher")[0]);
			dto.setYear(Integer.parseInt(reqParams.get("year")[0]));
			
			BookDao dao = new BookDao(ConnectionService.getInstance());
			dao.addNewBook(dto);
			
			vp.putViewParam("bookAdded", dto);
			return vp;
			
		} catch (Exception e){
			e.printStackTrace();
			vp.putViewParam("hasError", true);
			vp.putViewParam("errorMsg", generateFriendlyError(e));
			return vp;
		}
	}

	public ViewAndParams getNewBookCopyForm() {
		ViewAndParams vp = new ViewAndParams("/jsp/librarian/addNewBookCopyForm.jsp");
		return vp;
	}

	public ViewAndParams addNewBookCopy(HttpServletRequest request) {
		ViewAndParams vp = new ViewAndParams("/jsp/librarian/addNewBookCopyResults.jsp");
		return vp;
	}

	public ViewAndParams getRemoveBookForm() {
		ViewAndParams vp = new ViewAndParams("/jsp/librarian/removeBookForm.jsp");
		return vp;
	}

	public ViewAndParams removeBook(HttpServletRequest request) {
		ViewAndParams vp = new ViewAndParams("/jsp/librarian/removeBookResults.jsp");
		return vp;
	}
	
	/**
	 * Generates a user-friendly error message for various 
	 * types of exceptions
	 * @param e the exception
	 * @return a user-friendly error messsage
	 */
	public static String generateFriendlyError(Exception e){
		if(e instanceof NullPointerException){
			return "Please ensure all required fields are completed before submitting.";
		} else if (e instanceof NumberFormatException){
			return "Please ensure that numeric fields contain only numbers.";
		} else if (e instanceof SQLException){
			return e.getMessage() + ". Please correct the error and try again.";
		} else {
			return "There was a a problem completing your request. " + e.getMessage();
		}
	}
}
