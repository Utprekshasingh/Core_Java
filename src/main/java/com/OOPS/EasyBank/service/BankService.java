package com.OOPS.EasyBank.service;

import java.util.List;

import com.OOPS.EasyBank.entities.Account;
import com.OOPS.EasyBank.entities.User;

public interface BankService {

	User createUser(User user);

	Account createAccount(int userId);

	List<Account> getAccountsForUser(int userId);

	double checkBalance(long accNo);

	boolean deposit(long accNo, double amount);

	boolean withdraw(long accNo, double amount);
}
