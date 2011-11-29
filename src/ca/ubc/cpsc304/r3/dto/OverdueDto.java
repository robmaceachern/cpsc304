package ca.ubc.cpsc304.r3.dto;

import java.sql.Date;

public class OverdueDto {
	// variables to hold the actual data of an overdue item
		private int callNumber;
		private int borid;
		private String type;
		private String email;
		private String btitle;
		private String name;
		private Date duedate;

	    // default constructor
	    public OverdueDto(){}
	    
	    // constructor with initialized values
		public OverdueDto(int callNumber, int borid, String name, String type, String btitle, String email){
			this.callNumber = callNumber;
			this.borid = borid;
			this.name = name;
			this.type = type;
			this.btitle = btitle;
			this.email = email;
		}

		public int getCallNumber() {
			return callNumber;
		}

		public void setCallNumber(int callNumber) {
			this.callNumber = callNumber;
		}

		public int getBorid() {
			return borid;
		}

		public void setBorid(int borid) {
			this.borid = borid;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getEmail(){
			return email;
		}
		
		public void setEmail(String email){
			this.email = email;
		}
		
		public String getTitle(){
			return btitle;
		}
		
		public void setTitle( String title){
			btitle = title;
		}
		
		public String getName(){
			return name;
		}
		
		public void setName(String name){
			this.name = name;
		}
		
		public Date getDuedate(){
			return duedate;
		}
		
		public void setDuedate(Date date){
			duedate = date;
		}
	}

