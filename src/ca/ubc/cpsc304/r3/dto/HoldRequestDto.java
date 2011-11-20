package ca.ubc.cpsc304.r3.dto;

public class HoldRequestDto {
	// variables to hold the actual data of a hold request
	private int hid;
    private int bid;
    private int callNumber;
    private java.sql.Date issuedDate;
	
    // default constructor
    public HoldRequestDto(){}
    
    // constructor with initialised values
	public HoldRequestDto(int hid, int bid, int callNumber, java.sql.Date issuedDate){
		this.hid = hid;
		this.bid = bid;
		this.callNumber = callNumber;
		this.issuedDate = issuedDate;
	}
	
	// getters and setters for the data members

	public int getHid() {
		return hid;
	}

	public void setHid_(int hid) {
		this.hid = hid;
	}

	public int getBid() {
		return bid;
	}

	public void setBid(int bid) {
		this.bid = bid;
	}

	public int getCallNumber() {
		return callNumber;
	}

	public void setCallNumber(int callNumber) {
		this.callNumber = callNumber;
	}

	public java.sql.Date getIssuedDate() {
		return issuedDate;
	}

	public void setIssuedDate(java.sql.Date issuedDate) {
		this.issuedDate = issuedDate;
	}
}
