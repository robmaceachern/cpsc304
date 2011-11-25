package ca.ubc.cpsc304.r3.dto;

import java.sql.Date;

public class CheckedOutBookDto {

	private int callNumber;
	private int copyNo;
	private String title;
	private Date outDate;
	private Date dueDate;

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getOutDate() {
		return new Date(outDate.getTime());
	}

	public void setOutDate(Date outDate) {
		this.outDate = new Date(outDate.getTime());
	}

	public Date getDueDate() {
		return new Date(dueDate.getTime());
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = new Date(dueDate.getTime());
	}
}
