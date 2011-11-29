package ca.ubc.cpsc304.r3.dto;

public class ReturnDto {

	private String requestor;
	private String email;
	private String returner;
	boolean onHold = false;
	private int hid;
	private int fine = 0;

	public String getRequestor() {
		return requestor;
	}

	public void setRequestor(String requestor) {
		this.requestor = requestor;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getReturner() {
		return returner;
	}

	public void setReturner(String returner) {
		this.returner = returner;
	}

	public boolean isOnHold() {
		return onHold;
	}

	public void setHold(boolean b) {
		onHold = b;
	}

	public int getHoldID() {
		return hid;
	}

	public void setHoldID(int hold) {
		hid = hold;
	}

	public int getFine() {
		return fine;
	}

	public void setFine(int fine) {
		this.fine = fine;
	}
}
