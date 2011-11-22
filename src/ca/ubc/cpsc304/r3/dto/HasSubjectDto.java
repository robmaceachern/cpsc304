package ca.ubc.cpsc304.r3.dto;

public class HasSubjectDto {
	// variables to hold the actual data of a hold request
	private int callNumber;
	private String subject;

    // default constructor
    public HasSubjectDto(){}
    
    // constructor with initialised values
	public HasSubjectDto(int callNumber, String subject){
		this.callNumber = callNumber;
		this.subject = subject;
	}

	public int getCallNumber() {
		return callNumber;
	}

	public void setCallNumber(int callNumber) {
		this.callNumber = callNumber;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

}
