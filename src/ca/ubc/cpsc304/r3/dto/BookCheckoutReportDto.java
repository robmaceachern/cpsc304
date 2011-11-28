package ca.ubc.cpsc304.r3.dto;

/**
 * Dto to encapsulate book information 
 * and borrowing statistics.
 * @author Rob
 *
 */
public class BookCheckoutReportDto {

	private int callNumber;
	private String title;
	private String mainAuthor;
	private int borrowedCount;

	public int getCallNumber() {
		return callNumber;
	}

	public void setCallNumber(int callNumber) {
		this.callNumber = callNumber;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMainAuthor() {
		return mainAuthor;
	}

	public void setMainAuthor(String mainAuthor) {
		this.mainAuthor = mainAuthor;
	}

	public int getBorrowedCount() {
		return borrowedCount;
	}

	public void setBorrowedCount(int borrowedCount) {
		this.borrowedCount = borrowedCount;
	}

}
