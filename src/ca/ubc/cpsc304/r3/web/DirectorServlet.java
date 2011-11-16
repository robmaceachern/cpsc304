package ca.ubc.cpsc304.r3.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ca.ubc.cpsc304.r3.web.responder.HomePageController;

/**
 * This class receives all the user's web requests and then determines what controller
 * should handle it.
 */
public class DirectorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ViewAndParams view = getViewAndParams(request);
		
		// this takes any parameters and makes them available to the jsp
		for(String key : view.getViewParams().keySet()){
			request.setAttribute(key, view.getViewParams().get(key));
		}
		
		RequestDispatcher dispatch = getServletContext().getRequestDispatcher(view.getViewPath());
		dispatch.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// we'll need to implement this to handle POSTed data (such as forms)
	}
	
	/**
	 * 
	 * @param request the web request
	 * @return the ViewAndParams to display
	 */
	private ViewAndParams getViewAndParams(HttpServletRequest request){
		
		// can be something like "/booklist" or "/librarian/addbook"
		String requestPath = cleanUpPath(request.getPathInfo());
		
		// this will grow into a giant list of if/else statements as we 
		// add pages. It's not ideal, but it'll do the job.
		if(requestPath.equals("/")){
			return new HomePageController().getHomePage(request);
		}
		
		// an error page if nothing hits
		return new ViewAndParams("/jsp/error.jsp");
	}

	private String cleanUpPath(String pathInfo) {
		// treat empty or null as root
		if(pathInfo == null || pathInfo.isEmpty()){
			return "/";
		}
		
		char lastChar = pathInfo.charAt(pathInfo.length()-1);
		if(lastChar != '/'){
			return pathInfo + "/";
		}
		return pathInfo;
		
	}

	public static class ViewAndParams {

		private String viewPath;

		private Map<String, Object> viewParams;

		public ViewAndParams(String viewPath){
			if(viewPath == null) throw new NullPointerException();
			this.viewPath = viewPath;
			this.viewParams = new HashMap<String, Object>();
		}

		public void putViewParam(String name, Object o){
			this.viewParams.put(name, o);
		}
		
		public void putAllViewParams(Map<String, Object> params){
			this.viewParams.putAll(params);
		}
		
		public Map<String, Object> getViewParams(){
			return new HashMap<String, Object>(this.viewParams);
		}
		
		public String getViewPath(){
			return this.viewPath;
		}
	}

}
