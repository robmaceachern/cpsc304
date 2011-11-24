package ca.ubc.cpsc304.r3.web.responder;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ca.ubc.cpsc304.r3.db.BorrowingDao;
import ca.ubc.cpsc304.r3.db.ConnectionService;
import ca.ubc.cpsc304.r3.dto.BorrowingDto;
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
	
	public ViewAndParams getCheckAccountForm(){
		ViewAndParams vp = new ViewAndParams("/jsp/borrower/checkAccountForm.jsp");
		return vp;
	}

	public ViewAndParams getCheckAccountResults(HttpServletRequest request) {
		ViewAndParams vp = new ViewAndParams("/jsp/borrower/checkAccountResults.jsp");
		
		Integer bid = getIntFromParams("bid", request);
		if(bid==null){
			// TODO: do something nicer
		}
		else{
			BorrowingDao dao = new BorrowingDao(ConnectionService.getInstance());
			
			try {
				List<BorrowingDto> borrowedItems = dao.getUnpaidByID(bid);
				vp.putViewParam("borrowedItems", borrowedItems);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				// also catch / handle invalid input exception
			}
		}
		
		return vp;
	}
	
	private Integer getIntFromParams(String key, HttpServletRequest request){
		@SuppressWarnings("unchecked")
		Map<String, String[]> reqParams = request.getParameterMap();
		String[] params = reqParams.get(key);
		if(params.length!=1){
			return null;
		}
		else{
			try{
				Integer i = Integer.valueOf(params[0]);
				return i;
			}
			catch(NumberFormatException e){
				return null;
			}
		}
	}

}
