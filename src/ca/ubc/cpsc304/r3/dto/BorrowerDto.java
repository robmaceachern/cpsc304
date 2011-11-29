package ca.ubc.cpsc304.r3.dto;

public class BorrowerDto {

	String password;
	String name;
	int phone;
	String address;
	String email;
	int sin;
	String type;
	
	public BorrowerDto(){}
	
	public BorrowerDto(String pw, String name, int phone, String address,
			String email, int sin, String type){
		password = pw;
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.email = email;
		this.sin = sin;
		this.type = type;
	}
	
	public String getPassword(){
		return password;
	}
	
	public void setPassword(String pw){
		password = pw;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public int getPhone(){
		return phone;
	}
	
	public void setPhone(int num){
		phone = num;
	}
	
	public String getAddress(){
		return address;
	}
	
	public void setAddress(String add){
		address = add;
	}
	
	public String getEmail(){
		return email;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public int getSin(){
		return sin;
	}
	
	public void setSin(int sin){
		this.sin = sin;
	}
	
	public String getType(){
		return type;
	}
	
	public void setType(String btype){
		type = btype;
	}
}
