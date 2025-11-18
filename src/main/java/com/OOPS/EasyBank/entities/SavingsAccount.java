package com.OOPS.EasyBank.entities;

//Inheritance - It is child class of Account
public class SavingsAccount extends Account{

	private double interest;

	public SavingsAccount() {}
	

	public SavingsAccount(Long accountNumber, int userId, double balance, double interest) {
		super(accountNumber, userId, balance);
		this.interest = interest;
	}

	//Polymorphism
	@Override
   public boolean withdraw(double amount) {
    	//restrict large withdrawals
    	if(amount>10000) {
    		return false;
    	}
    	return super.withdraw(amount);
    }


	public double getInterest() {
		return interest;
	}

	public void setInterest(double interest) {
		this.interest = interest;
	}
	
	
}
