package ca.ubc.cpsc304.r3.web.responder;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ca.ubc.cpsc304.r3.db.BorrowingDao;
import ca.ubc.cpsc304.r3.db.HoldRequestDao;
import ca.ubc.cpsc304.r3.db.ConnectionService;
import ca.ubc.cpsc304.r3.db.FineDao;
import ca.ubc.cpsc304.r3.dto.BorrowingDto;
import ca.ubc.cpsc304.r3.dto.FineDto;
import ca.ubc.cpsc304.r3.dto.HoldRequestDto;
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
		
		try {
			@SuppressWarnings("unchecked")
		
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
			vp.putViewParam("errorMsg", generateFriendlyError(e));
			return vp;
		}
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
