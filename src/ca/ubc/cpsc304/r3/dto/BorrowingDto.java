package ca.ubc.cpsc304.r3.dto;

public class BorrowingDto {
	// variables to hold the actual data of a hold request
	private int borid;
	private int bid;
	private int callNumber;
    private int copyNo;
    private java.sql.Date inDate;
    private java.sql.Date outDate;
	
    // default constructor
    public BorrowingDto(){}
    
    // constructor with initialised values
	public BorrowingDto(int borid, int bid, int callNumber, int copyNo,
			            java.sql.Date inDate, java.sql.Date outDate){
		this.borid = borid;
		this.bid = bid;
		this.callNumber = callNumber;
		this.copyNo = copyNo;
		this.inDate = inDate;
		this.outDate = outDate;
	}

	public int getBorid() {
		return borid;
	}

	public void setBorid(int borid) {
		this.borid = borid;
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

	public int getCopyNo() {
		return copyNo;
	}

	public void setCopyNo(int copyNo) {
		this.copyNo = copyNo;
	}

	public java.sql.Date getInDate() {
		return inDate;
	}

	public void setInDate(java.sql.Date inDate) {
		this.inDate = inDate;
	}

	public java.sql.Date getOutDate() {
		return outDate;
	}

	public void setOutDate(java.sql.Date outDate) {
		this.outDate = outDate;
	}
}
