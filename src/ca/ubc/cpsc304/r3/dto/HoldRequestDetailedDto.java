package ca.ubc.cpsc304.r3.dto;

public class HoldRequestDetailedDto extends HoldRequestDto {
	private int callNumber;
	private String title;
	private String mainAuthor;
	
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
}
