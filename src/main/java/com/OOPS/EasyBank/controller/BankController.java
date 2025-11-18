package com.OOPS.EasyBank.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.OOPS.EasyBank.entities.Account;
import com.OOPS.EasyBank.entities.User;
import com.OOPS.EasyBank.service.BankService;

@RestController
@RequestMapping("/api")
public class BankController {

	@Autowired
	private BankService service;
	
	//Create user
	@PostMapping("/users")
	public ResponseEntity<User> createUser(@RequestBody User user){
		return ResponseEntity.ok(service.createUser(user));
	}
	
	//create account
	@PostMapping("/users/{userId}/accounts")
	//we used @PathVariable because REST says if a resource belongs to another resource, its URL should reflect that(Account belongs to User)
	//use @RequestParam when filter or option
	public ResponseEntity<Account> createAccount(@PathVariable int userId){
		return ResponseEntity.ok(service.createAccount(userId));
	}
	
	//fetch accounts of a user
	@GetMapping("/users/{userId}/accounts")
	public ResponseEntity<List<Account>> getUserAccounts(@PathVariable int userId){
		return ResponseEntity.ok(service.getAccountsForUser(userId));
	}
	
	//check balance
	@GetMapping("/account/{accNo}/balance")
	public ResponseEntity<Map<String,Object>> checkBalance(@PathVariable long accNo){
		return ResponseEntity.ok(Map.of("Balance:",service.checkBalance(accNo)));
	}
	
	//deposit amount
	@PostMapping("/account/{accNo}/deposit")
	//req is used when simple values and no entity class matches the json structure
	//Map<String,Object> use Object when coming values contains two different datatype values in json
	public ResponseEntity<Map<String,Object>> deposit(@PathVariable long accNo, @RequestBody Map<String, Double> req){
		boolean success = service.deposit(accNo, req.get("amount"));
		if(success) {
		return ResponseEntity.ok(Map.of("message:","deposit successful!!", "balance", service.checkBalance(accNo)));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message","Enter Valid Amount!!"));
	}
	
	//withdraw amount
	@PostMapping("/account/{accNo}/withdraw")
	public ResponseEntity<Map<String, Object>> withdraw(@PathVariable long accNo, @RequestBody Map<String, Double> req){
		boolean success = service.withdraw(accNo, req.get("amount"));
		if(success) {
			return ResponseEntity.ok(Map.of("message:","withdraw successful!!", "balance", service.checkBalance(accNo)));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message","Withdrawal failed! Insufficient balance."));
	}
}
