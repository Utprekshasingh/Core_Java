package com.OOPS.EasyBank.repositories;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.OOPS.EasyBank.entities.Account;
import com.OOPS.EasyBank.entities.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository //To make this a Spring managed bean
public class FileBankRepository implements BankRepository{

	//it creates a datatype "File" type which cannot be reassigned new path due to final
	//and it stores the reference to given file path on local computer 
	private final File userFile = new File("data/users.json");
	private final File accountFile = new File("data/accounts.json");
	
	//ObjectMapper is a class from Jackson library user for JSON Operations:
	//convert Java object -> JSON and vice versa commonly user for JSON serialization/deserialization
	//the variable mapper will be used everywhere to read/write JSON
	private final ObjectMapper mapper = new ObjectMapper();
	
	
	public FileBankRepository() {
		try {
			//if the parent file does not exist it creates it
			if(!userFile.getParentFile().exists()) {
				userFile.getParentFile().mkdir();
			}
			//if userFile does not exist in path then create empty file(so no error when reads) ->write an empty list of users into it
			//with help of mapper "ObjectMapper function write JSON data(where to write, what to write)
			if(!userFile.exists()) {
				mapper.writeValue(userFile, new ArrayList<User>());
			}
			if(!accountFile.exists()) {
				mapper.writeValue(accountFile, new ArrayList<Account>());
			}
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
	//synchronized - Only one thread can run this method at a time , important to prevent file corruption
	//read/write json file is not thread safe, if thread-a reads the file and thread-b writes the file at same time
	//JSON becomes corrupted, synchronized locks the method so only one thread uses the file at a time
	private synchronized List<User> readUsers() throws Exception//might throw errors(file not found, bad json, IO error
	{
		//Typereference is used because java looses generic type info at runtime and
		//Jackson needs this extra help to correctly convert JSON into a list<User> or else
		//without Typereference Jackson can't know which type of objects are inside the list User or Account
		return mapper.readValue(userFile, new TypeReference<List<User>>() {});
	}
	
	private synchronized void writeUsers(List<User> users)throws Exception{
		//writerWuthDefaultPrettyPrinter - formats the JSON nicel with line breaks, indentation and
		//readable structure instead of writing everything in one long line
		mapper.writerWithDefaultPrettyPrinter().writeValue(userFile, users);
	}
	
	private synchronized List<Account> readAccounts() throws Exception{
		return mapper.readValue(accountFile, new TypeReference<List<Account>>() {});
	}
	
	private synchronized void writeAccounts(List<Account> accounts) throws Exception{
		mapper.writerWithDefaultPrettyPrinter().writeValue(accountFile, accounts);
	}


	@Override
	//Implemented methods from interface should be public because those methods are automatically public
	public User saveUser(User user) {
		try {
			List<User> currentUsers=readUsers();
			int newId = currentUsers.stream().mapToInt(User::getId).max().orElse(0)+1;
			user.setId(newId);
			currentUsers.add(user);
			writeUsers(currentUsers);
		return user;
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}


	@Override
	public Account saveAccount(Account account) {
		try {
			List<Account> currentAccounts = readAccounts();
			long newAccNo = currentAccounts.stream().mapToLong(Account::getAccountNumber).max().orElse(1000000)+1;
			account.setAccountNumber(newAccNo);
			currentAccounts.add(account);
			writeAccounts(currentAccounts);
		return account;
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}


	@Override
	public List<Account> findAccountsByUserId(int userId) {
		try {
			return readAccounts().stream().filter(e->e.getUserId()==userId).collect(Collectors.toList());
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}


	@Override
	public Account findAccountByAccountNo(long accNo) {
		try{
			return readAccounts().stream().filter(e->e.getAccountNumber()==accNo).findFirst().orElseThrow();
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}


	@Override
	public void updateAccount(Account acc) {
		try {
			List<Account> accounts = readAccounts();
			for(int i=0;i<accounts.size();i++) {
				if(accounts.get(i).getAccountNumber()==acc.getAccountNumber()) {
					accounts.set(i, acc);
					writeAccounts(accounts);
					return;
				}
			}
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}





	
	
}
