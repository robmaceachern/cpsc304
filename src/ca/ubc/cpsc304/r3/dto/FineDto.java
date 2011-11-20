package ca.ubc.cpsc304.r3.dto;

public class FineDto {
	// variables to hold the actual data of a hold request
	private int fid;
    private int amount;
    private int borid;
    private java.sql.Date issuedDate;
    private java.sql.Date paidDate;

    // default constructor
    public FineDto(){}
    
    // constructor with initialised values
	public FineDto(int fid, int amount, java.sql.Date issuedDate, 
			       java.sql.Date paidDate, int borid){
		this.fid = fid;
		this.amount = amount;
		this.issuedDate = issuedDate;
		this.paidDate = paidDate;
		this.borid = borid;
	}

	public int getFid() {
		return fid;
	}

	public void setFid(int fid) {
		this.fid = fid;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public java.sql.Date getIssuedDate() {
		return issuedDate;
	}

	public void setIssuedDate(java.sql.Date issuedDate) {
		this.issuedDate = issuedDate;
	}

	public java.sql.Date getPaidDate() {
		return paidDate;
	}

	public void setPaidDate(java.sql.Date paidDate) {
		this.paidDate = paidDate;
	}

	public int getBorid() {
		return borid;
	}

	public void setBorid(int borid) {
		this.borid = borid;
	}
}
