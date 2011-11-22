package ca.ubc.cpsc304.r3.dto;

public class BookDto {
	// variables to hold the actual data of a hold request
	private int callNumber;
	private int isbn;
	private String title;
	private String mainAuthor;
	private String publisher;
	private int year;

    // default constructor
    public BookDto(){}
    
    // constructor with initialised values
	public BookDto(int callNumber, int isbn, String title, String mainAuthor, String publisher, int year){
		this.callNumber = callNumber;
		this.isbn = isbn;
		this.title = title;
		this.mainAuthor = mainAuthor;
		this.publisher = publisher;
		this.year = year;
	}

	public int getCallNumber() {
		return callNumber;
	}

	public void setCallNumber(int callNumber) {
		this.callNumber = callNumber;
	}

	public int getIsbn() {
		return isbn;
	}

	public void setIsbn(int isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMainAuthor() {
		return mainAuthor;
	}

	public void setMainAuthor(String mainAuthor) {
		this.mainAuthor = mainAuthor;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

}
