package ca.ubc.cpsc304.r3.web.responder;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ca.ubc.cpsc304.r3.DNEException;
import ca.ubc.cpsc304.r3.db.BookDao;
import ca.ubc.cpsc304.r3.db.BorrowerDao;
import ca.ubc.cpsc304.r3.db.BorrowingDao;
import ca.ubc.cpsc304.r3.db.ConnectionService;
import ca.ubc.cpsc304.r3.db.FineDao;
import ca.ubc.cpsc304.r3.db.HoldRequestDao;
import ca.ubc.cpsc304.r3.dto.BookDto;
import ca.ubc.cpsc304.r3.dto.BorrowerDto;
import ca.ubc.cpsc304.r3.dto.BorrowingDto;
import ca.ubc.cpsc304.r3.dto.FineDto;
import ca.ubc.cpsc304.r3.dto.HoldRequestDto;
import ca.ubc.cpsc304.r3.web.DirectorServlet.ViewAndParams;

public class BorrowerController {
	private String numErrorMsg = "Please make sure you enter valid numbers.";

	/**
	 * From requirements: Remove a borrower from the library. The borrow id is
	 * provided.
	 * 
	 * @param request
	 *            the http request
	 * @return the removeBorrowerResults view and the associated parameters
	 */
	public ViewAndParams removeBorrower(HttpServletRequest request) {

		ViewAndParams vp = new ViewAndParams(
				"/jsp/librarian/removeBorrowerResults.jsp");
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
		ViewAndParams vp = new ViewAndParams(
				"/jsp/librarian/removeBorrowerForm.jsp");
		return vp;
	}

	public ViewAndParams addNewBorrower(HttpServletRequest request) {
		ViewAndParams vp = new ViewAndParams(
				"/jsp/clerk/addNewBorrowerResults.jsp");
		boolean hasError = false;

		try {
			BorrowerDao bdao = new BorrowerDao(ConnectionService.getInstance());
			BorrowerDto bdto = new BorrowerDto();
			Map<String, String[]> reqParams = request.getParameterMap();
			bdto.setAddress(reqParams.get("address")[0]);
			bdto.setName(reqParams.get("name")[0]);
			bdto.setEmail(reqParams.get("email")[0]);
			bdto.setPassword(reqParams.get("password")[0]);
			bdto.setPhone(Integer.valueOf(reqParams.get("phone")[0]));
			bdto.setType(reqParams.get("btype")[0]);
			bdto.setSin(Integer.valueOf(reqParams.get("sin")[0]));
			int bid = bdao.addBorrower(bdto);
			vp.putViewParam("bname", reqParams.get("name")[0]);
			vp.putViewParam("bid", bid);
		} catch (Exception e) {
			hasError = true;
			e.printStackTrace();
			if (e instanceof NumberFormatException)
				vp.putViewParam("errorMsg", numErrorMsg);
			else if (e instanceof DNEException)
				vp.putViewParam("errorMsg", e.getMessage());
			else if(e instanceof SQLException)
				vp.putViewParam("error", e.getMessage());
			else
				vp.putViewParam(
						"errorMeg",
						"There was an error adding the borrower.<br>"
								+ "Please make sure all values are entered correctly.");
		}
		finally {
			vp.putViewParam("hasError", hasError);
		}
		
		return vp;
	}

	public ViewAndParams getAddNewBorrowerForm() {
		ViewAndParams vp = new ViewAndParams(
				"/jsp/clerk/addNewBorrowerForm.jsp");
		return vp;
	}

	public ViewAndParams getCheckAccountForm() {
		ViewAndParams vp = new ViewAndParams(
				"/jsp/borrower/checkAccountForm.jsp");
		return vp;
	}

	public ViewAndParams getCheckAccountResults(HttpServletRequest request) {
		ViewAndParams vp = new ViewAndParams(
				"/jsp/borrower/checkAccountResults.jsp");

		try {

			Integer bid = getIntFromParams("bid", request);

			// create dao's for the 3 different queries
			BorrowingDao daoBorrowing = new BorrowingDao(
					ConnectionService.getInstance());
			FineDao daoFine = new FineDao(ConnectionService.getInstance());
			HoldRequestDao daoHoldRequest = new HoldRequestDao(
					ConnectionService.getInstance());
			
			// check to make sure the bid is valid
			BorrowerDao daoBorower = new BorrowerDao(ConnectionService.getInstance());
			
			if(!daoBorower.checkValidBorid(bid)){
				Exception badBorid = new Exception("Unrecognized borrower ID entered.");
				throw badBorid;
			}
			else{

				// get the results of the 3 queries
				List<BorrowingDto> borrowedItems = daoBorrowing
						.getBorrowedByID(bid);
				List<FineDto> outstandingFines = daoFine.getUnpaidByID(bid);
				List<HoldRequestDto> currentHolds = daoHoldRequest.getByID(bid);
	
				// attach the results to give them to the web page
				vp.putViewParam("borrowedItems", borrowedItems);
				vp.putViewParam("outstandingFines", outstandingFines);
				vp.putViewParam("currentHolds", currentHolds);
	
				return vp;
			}
		} catch (Exception e) {
			e.printStackTrace();
			vp.putViewParam("hasError", true);
			vp.putViewParam("errorMsg", BookController.generateFriendlyError(e));
			return vp;
		}
	}

	private Integer getIntFromParams(String key, HttpServletRequest request) {
		@SuppressWarnings("unchecked")
		Map<String, String[]> reqParams = request.getParameterMap();
		String[] params = reqParams.get(key);

		Integer i = Integer.valueOf(params[0]);
		return i;
	}
	
	public ViewAndParams getHoldRequestForm() {
		ViewAndParams vp = new ViewAndParams(
				"/jsp/borrower/holdRequestForm.jsp");
		return vp;
	}

	public ViewAndParams placeHoldRequestResults(HttpServletRequest request) {
		ViewAndParams vp = new ViewAndParams("/jsp/borrower/holdRequestResults.jsp");
		try {
			@SuppressWarnings("unchecked")
			HoldRequestDao dao = new HoldRequestDao(ConnectionService.getInstance());
			Map<String, String[]> reqParams = request.getParameterMap();
			
			Integer callNo = Integer.valueOf(reqParams.get("callNumber")[0]);
			Integer borId = Integer.valueOf(reqParams.get("bid")[0]);
			
			BookDao daoB = new BookDao(ConnectionService.getInstance());
			List<BookDto> books =daoB.getByCallNumber(callNo);
			
			BorrowerDao daoBo = new BorrowerDao(ConnectionService.getInstance());
			
			if(books.size()!=1){
				Exception badCallNo = new Exception("Invalid call number entered");
				throw badCallNo;
			}
			else if(!daoBo.checkValidBorid(borId)){
				Exception badBorid = new Exception("Invalid borrower ID entered");
				throw badBorid;
			}
			else{
				dao.placeByCallNumberAndID(callNo, borId);
			}
		} catch (Exception e){
			vp.putViewParam("hasError", true);
			vp.putViewParam("errorMsg", BookController.generateFriendlyError(e));
			return vp;
		}

		return vp;
	}

}
