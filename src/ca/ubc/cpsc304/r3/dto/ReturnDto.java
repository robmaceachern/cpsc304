package ca.ubc.cpsc304.r3.dto;

public class ReturnDto {

	private String name;
	private String email;
	boolean onHold = false;
	private int hid;
	private int fine = 0;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
