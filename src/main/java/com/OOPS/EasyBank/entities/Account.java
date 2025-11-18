package com.OOPS.EasyBank.entities;


//Parent class of SavingsAccount
public class Account {

	private long accountNumber;
	private int userId;
	private double balance;
	
	public Account() {}

	public Account(long accountNumber, int userId, double balance) {
		super();
		this.accountNumber = accountNumber;
		this.userId = userId;
		this.balance = balance;
	}

	//Encapsulation
	public long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	//we can use this to check balance
	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	// Behavior (methods) = encapsulation of logic
	public boolean deposit(double amount) {
		if(amount>0) {
			balance+=amount;
			return true;
		}
		return false;
	}
	
	public boolean withdraw(double amount) {
		if(amount>0 && balance>=amount) {
			balance-=amount;
			System.out.println("Withdrawal successful, new balance: " + balance);
			return true;
		}
		System.out.println("Withdrawal failed!");
		return false;
	}

}
