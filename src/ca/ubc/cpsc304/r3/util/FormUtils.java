package ca.ubc.cpsc304.r3.util;

import java.sql.SQLException;
import java.util.Map;
import java.util.Set;


public class FormUtils {

	/**
	 * Only static methods on this class
	 */
	private FormUtils() {
	}

	/**
	 * Generates a user-friendly error message for various types of exceptions
	 * 
	 * @param e
	 *            the exception
	 * @return a user-friendly error messsage
	 */
	public static String generateFriendlyError(Exception e) {
		if (e instanceof NumberFormatException) {
			return "Please ensure that numeric fields contain only numbers.";
		} else if (e instanceof DNEException) {
			return e.getMessage();
		} else if (e instanceof SQLException) {
			if (((SQLException) e).getErrorCode() == 1452
					|| ((SQLException) e).getErrorCode() == 0) {
				return "You are attempting to reference data that does not exit in the library! Please try again.";
			}
			return e.getMessage()
					+ ". Please correct the error and try again. Error code: "
					+ ((SQLException) e).getErrorCode();
		} else if (e instanceof IllegalArgumentException) {
			return "Please ensure all fields are completed before submitting.";
		} else {
			return "There was a a problem completing your request. "
					+ e.getMessage();
		}
	}

	/**
	 * Throws an IllegalArgumentException if any value in the map is an empty
	 * String.
	 * 
	 * @param requestParams
	 *            a map of parameters
	 */
	public static void checkForBadInput(Map<String, String[]> requestParams) {
		Set<String> keys = requestParams.keySet();
		for (String key : keys) {
			if (requestParams.get(key)[0].isEmpty()) {
				throw new IllegalArgumentException();
			}
		}
	}

}
