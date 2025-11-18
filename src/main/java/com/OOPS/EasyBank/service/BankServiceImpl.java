package com.OOPS.EasyBank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.OOPS.EasyBank.entities.Account;
import com.OOPS.EasyBank.entities.User;
import com.OOPS.EasyBank.repositories.BankRepository;

@Service
public class BankServiceImpl implements BankService{

	@Autowired
	private BankRepository repo;

	@Override
	public User createUser(User user) {
		return repo.saveUser(user);
	}

	@Override
	public Account createAccount(int userId) {
		Account acc = new Account(0, userId, 0.0);
		return repo.saveAccount(acc);
	}

	@Override
	public List<Account> getAccountsForUser(int userId) {
		return repo.findAccountsByUserId(userId);
	}

	@Override
	public double checkBalance(long accNo) {
		Account acc = repo.findAccountByAccountNo(accNo);
		return acc.getBalance();
	}

	@Override
	public boolean deposit(long accNo, double amount) {
		Account acc = repo.findAccountByAccountNo(accNo);
		boolean success = acc.deposit(amount);
		if(success) {
		repo.updateAccount(acc);
		return true;
		}else {
		repo.updateAccount(acc);
		return false;
		}
	}

	@Override
	public boolean withdraw(long accNo, double amount) {
		Account acc = repo.findAccountByAccountNo(accNo);
		boolean success = acc.withdraw(amount);
		if(success) {
		repo.updateAccount(acc);
		return true;
		}else {
		repo.updateAccount(acc);
		return false;
		}
	}


}
