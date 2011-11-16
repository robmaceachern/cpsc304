package ca.ubc.cpsc304.r3.web.responder;

import javax.servlet.http.HttpServletRequest;

import ca.ubc.cpsc304.r3.web.DirectorServlet.ViewAndParams;

public class HomePageController {
	
	public ViewAndParams getHomePage(HttpServletRequest request) {
		// this is the jsp this view uses
		ViewAndParams view = new ViewAndParams("/jsp/home.jsp");
		// setting a parameter that the view can display
		view.putViewParam("someVariable", "Hey guys what's going on?? This is jsp displaying a parameter!");
		return view;
	}
}
