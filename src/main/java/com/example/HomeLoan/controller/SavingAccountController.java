package com.example.HomeLoan.controller;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.HomeLoan.model.SavingAccount;
import com.example.HomeLoan.service.SavingAccountService;
import com.example.HomeLoan.service.utility;

@RestController
@RequestMapping("/SavingAccount")
public class SavingAccountController {
	@Autowired
	private SavingAccountService service;

	
	@Autowired
	private utility util;
	

	@PostMapping(value = "/createSavingAccout")
	public ResponseEntity<?> createSavingAccount(@RequestBody SavingAccount SavingAccountobj, HttpSession session) {
		try {
			Map<String, Object> body = new LinkedHashMap<>();
			return new ResponseEntity<>(service.saveBalance(SavingAccountobj, session), HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>("Error occurred during saving account creation", HttpStatus.INTERNAL_SERVER_ERROR);
		}


	}

	@GetMapping(value = "/UserById/{user}")
	public ResponseEntity<?> findSavingAccountByUserid(@PathVariable int user, HttpSession session) {

		try {
			Map<String, Object> body = new LinkedHashMap<>();
			return new ResponseEntity<>(service.findAccountByUserId(user), HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>("Error occurred during fetching saving account", HttpStatus.INTERNAL_SERVER_ERROR);
		}


	}

	@RequestMapping(value = "/applyLoan/{user_id}", produces = "application/json",
			method = {RequestMethod.GET, RequestMethod.PUT})

	public ResponseEntity<?> getAccdetails(@PathVariable int user_id, HttpSession session) {


		try {
			Map<String, Object> body = new LinkedHashMap<>();
			return new ResponseEntity<>(this.service.getAccDetails(user_id), HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>("Error during apply loan", HttpStatus.INTERNAL_SERVER_ERROR);
		}


	}






}
