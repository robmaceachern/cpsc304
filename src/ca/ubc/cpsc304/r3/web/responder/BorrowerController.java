package ca.ubc.cpsc304.r3.web.responder;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ca.ubc.cpsc304.r3.db.BorrowingDao;
import ca.ubc.cpsc304.r3.db.ConnectionService;
import ca.ubc.cpsc304.r3.db.FineDao;
import ca.ubc.cpsc304.r3.db.HoldRequestDao;
import ca.ubc.cpsc304.r3.dto.BorrowingDto;
import ca.ubc.cpsc304.r3.dto.FineDto;
import ca.ubc.cpsc304.r3.dto.HoldRequestDto;
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
	
	public ViewAndParams getCheckAccountForm(){
		ViewAndParams vp = new ViewAndParams("/jsp/borrower/checkAccountForm.jsp");
		return vp;
	}

	public ViewAndParams getCheckAccountResults(HttpServletRequest request) {
		ViewAndParams vp = new ViewAndParams("/jsp/borrower/checkAccountResults.jsp");
		
		try {
		
			Integer bid = getIntFromParams("bid", request);

			// create dao's for the 3 different queries
			BorrowingDao daoBorrowing = new BorrowingDao(ConnectionService.getInstance());	
			FineDao daoFine = new FineDao(ConnectionService.getInstance());
			HoldRequestDao daoHoldRequest = new HoldRequestDao(ConnectionService.getInstance());
			
			// get the results of the 3 queries
			List<BorrowingDto> borrowedItems = daoBorrowing.getBorrowedByID(bid);
			List<FineDto> outstandingFines = daoFine.getUnpaidByID(bid);
			List<HoldRequestDto> currentHolds = daoHoldRequest.getByID(bid);
			
			// attach the results to give them to the web page
			vp.putViewParam("borrowedItems", borrowedItems);
			vp.putViewParam("outstandingFines", outstandingFines);
			vp.putViewParam("currentHolds", currentHolds);
			
			return vp;
		} catch (Exception e) {
			e.printStackTrace();
			vp.putViewParam("hasError", true);
			vp.putViewParam("errorMsg", BookController.generateFriendlyError(e));
			return vp;
		}
	}
	
	private Integer getIntFromParams(String key, HttpServletRequest request){
		@SuppressWarnings("unchecked")
		Map<String, String[]> reqParams = request.getParameterMap();
		String[] params = reqParams.get(key);

		Integer i = Integer.valueOf(params[0]);
		return i;
	}

}
