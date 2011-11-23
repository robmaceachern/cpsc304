package ca.ubc.cpsc304.r3.dto;

public class HasAuthorDto {
	// variables to hold the actual data of a hold request
	private int callNumber;
	private String author;

    // default constructor
    public HasAuthorDto(){}
    
    // constructor with initialised values
	public HasAuthorDto(int callNumber, String author){
		this.callNumber = callNumber;
		this.author = author;
	}

	public int getCallNumber() {
		return callNumber;
	}

	public void setCallNumber(int callNumber) {
		this.callNumber = callNumber;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
}
