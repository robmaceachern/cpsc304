package ca.ubc.cpsc304.r3.web.responder;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import ca.ubc.cpsc304.r3.db.BookDao;
import ca.ubc.cpsc304.r3.db.BookDao.TableDto;
import ca.ubc.cpsc304.r3.db.ConnectionService;
import ca.ubc.cpsc304.r3.web.DirectorServlet.ViewAndParams;

public class HomePageController {
	
	public ViewAndParams getHomePage(HttpServletRequest request) {
		// this is the jsp this view uses
		ViewAndParams view = new ViewAndParams("/jsp/home.jsp");
		// setting a parameter that the view can display
		view.putViewParam("someVariable", "Hey guys what's going on?? This is jsp displaying a parameter!");
		
		// fetching some data from the database
		// construct a new dao when you need it. It requires a connection service
		BookDao bookDao = new BookDao(ConnectionService.getInstance());
		try {
			List<TableDto> dto = bookDao.getDatabaseTableNames();
			
			// put the query results into the view.
			// see how the jsp handles them
			view.putViewParam("tables", dto);
		} catch (SQLException e) {
			// hopefully do something more useful with these errors
			// maybe display a warning to the user or something
			e.printStackTrace();
		}
		return view;
	}
}
