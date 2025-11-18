package com.OOPS.EasyBank.repositories;

import java.util.List;

import com.OOPS.EasyBank.entities.Account;
import com.OOPS.EasyBank.entities.User;

//Abstraction
public interface BankRepository {

	//Every method inside Interface is automatically public and abstract hence no need to write that
	User saveUser(User user);

	Account saveAccount(Account account);

	List<Account> findAccountsByUserId(int userId);

	Account findAccountByAccountNo(long accNo);

	void updateAccount(Account acc);
}
