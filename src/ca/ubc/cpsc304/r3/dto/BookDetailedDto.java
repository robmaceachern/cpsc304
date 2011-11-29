package ca.ubc.cpsc304.r3.dto;

public class BookDetailedDto extends BookDto {
	int numCopiesOut;
	int numCopiesIn;
	public int getNumCopiesOut() {
		return numCopiesOut;
	}
	public void setNumCopiesOut(int numCopiesOut) {
		this.numCopiesOut = numCopiesOut;
	}
	public int getNumCopiesIn() {
		return numCopiesIn;
	}
	public void setNumCopiesIn(int numCopiesIn) {
		this.numCopiesIn = numCopiesIn;
	}

}
