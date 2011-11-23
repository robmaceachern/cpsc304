package ca.ubc.cpsc304.r3.dto;

public class BookCopyDto {
	// variables to hold the actual data of a hold request
	private int callNumber;
	private int copyNo;
	private String status;

    // default constructor
    public BookCopyDto(){}
    
    // constructor with initialised values
	public BookCopyDto(int callNumber, int copyNo, String status){
		this.callNumber = callNumber;
		this.copyNo = copyNo;
		this.status = status;
	}

	public int getCallNumber() {
		return callNumber;
	}

	public void setCallNumber(int callNumber) {
		this.callNumber = callNumber;
	}

	public int getCopyNo() {
		return copyNo;
	}

	public void setCopyNo(int copyNo) {
		this.copyNo = copyNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
