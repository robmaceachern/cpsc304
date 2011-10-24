package ca.ubc.cpsc304.r3;

public class Clerk {
	
	public String fuckThePolice(){
		String ftp = "FUCK THE POLICE!! http://www.youtube.com/watch?v=1M8vei3L0L8";
		ftp = ftp + System.getProperty("line.separator") + "Clerks for life. Word";
		return ftp;
	}
	
	public static void main(String[] args){
		Clerk badAssClerk = new Clerk();
		System.out.println(badAssClerk.fuckThePolice());
	}
}
