package com.OOPS.EasyBank.entities;

public class User {

	private int id;
	private String name;
	private String email;
	
	//Frameworks creates object using reflection → they can only call a constructor with no parameters.
	//It creates an empty User object with default values (0, null, etc.).
    //Later Jackson fills the fields using setters.
	public User() {}
	
	
	//Helps you create fully-initialized objects easily
	public User(int id, String name, String email) {
		super();//This is automatically given by java to call default parent Object default constructor to use methods like toString() and hashCode()
		this.id = id;
		this.name = name;
		this.email = email;
	}
	
	//Encapsulation (private + getters/setters)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
	
	//helpful in debugging
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email+ "]";
	}
	
	
}
