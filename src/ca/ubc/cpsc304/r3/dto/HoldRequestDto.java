package ca.ubc.cpsc304.r3.dto;

public class HoldRequestDto {
	// variables to hold the actual data of a hold request
	private int hid_;
    private int bid_;
    private int callNumber_;
    private java.sql.Date issuedDate_;
	
    // default constructor
    public HoldRequestDto(){}
    
    // constructor with initialised values
	public HoldRequestDto(int hid, int bid, int callNumber, java.sql.Date issuedDate){
		hid_ = hid;
		bid_ = bid;
		callNumber_ = callNumber;
		issuedDate_ = issuedDate;
	}
	
	// getters and setters for the data members

	public int getHid_() {
		return hid_;
	}

	public void setHid_(int hid_) {
		this.hid_ = hid_;
	}

	public int getBid_() {
		return bid_;
	}

	public void setBid_(int bid_) {
		this.bid_ = bid_;
	}

	public int getCallNumber_() {
		return callNumber_;
	}

	public void setCallNumber_(int callNumber_) {
		this.callNumber_ = callNumber_;
	}

	public java.sql.Date getIssuedDate_() {
		return issuedDate_;
	}

	public void setIssuedDate_(java.sql.Date issuedDate_) {
		this.issuedDate_ = issuedDate_;
	}
}
