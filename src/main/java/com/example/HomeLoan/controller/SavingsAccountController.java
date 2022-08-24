package com.example.HomeLoan.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.HomeLoan.model.SavingAccount;
import com.example.HomeLoan.service.SavingAccountService;

@RestController
@RequestMapping("/savingsAccount")
public class SavingsAccountController {
	@Autowired
	private SavingAccountService service;


	@PostMapping(value = "/createSavingsAccount")
	public ResponseEntity<?> createSavingAccount(@RequestBody SavingAccount SavingAccountobj, HttpSession session) {
		try {
			return new ResponseEntity<>(service.saveBalance(SavingAccountobj, session), HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>("Sorry. An error occured during savings account creation", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/applyLoan/{user_id}", produces = "application/json",
			method = {RequestMethod.GET})

	public ResponseEntity<?> getAccdetails(@PathVariable int user_id, HttpSession session) {


		try {
			return new ResponseEntity<>(this.service.getAccDetails(user_id), HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>("Sorry. An error occured during loan application", HttpStatus.INTERNAL_SERVER_ERROR);
		}


	}






}
