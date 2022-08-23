package com.example.HomeLoan.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.HomeLoan.model.LoanAccount;
import com.example.HomeLoan.repo.UserRepository;
import com.example.HomeLoan.service.LoanAccountService;
import com.example.HomeLoan.service.SavingAccountService;
import com.example.HomeLoan.service.utility;

@RequestMapping("/loan")
@RestController
@Validated
public class LoanController {
	
	private static final Logger logger = LogManager.getLogger(LoanController.class);
	
	@Autowired
	private LoanAccountService loanAccService;
	
	@Autowired
	private SavingAccountService savingAccountService;

	
	@Autowired
	private UserRepository userRepository;
	

	@Autowired
	private utility util;

	@RequestMapping(value = "/applyLoan", produces = "application/json",
	  		  method = {RequestMethod.GET, RequestMethod.PUT})
	public ResponseEntity<?> getAccdetails(HttpSession session)
	{
		if(util.sessionCheck(session).getStatusCodeValue()==405)
			return new ResponseEntity<>("please login!", HttpStatus.METHOD_NOT_ALLOWED);
		int user_id = (int) session.getAttribute("user_id");
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("prefilled details Acc", savingAccountService.getAccDetails(user_id));
		return new ResponseEntity<>(savingAccountService.getAccDetails(user_id), HttpStatus.OK);

	}
	
	@RequestMapping(value = {"/applyLoan","/approveLoan"}, method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public ResponseEntity<?> applyForLoan(@RequestBody @Valid  LoanAccount loanAcc ,HttpSession session) {
		logger.info(loanAcc.getAccountNo());
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("status","Congrats");
		return new ResponseEntity<>(body, HttpStatus.OK);		
	}
	
	@RequestMapping(value = {"/viewloan/{loan_id}"}, method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public ResponseEntity<?> viewForLoan(@PathVariable int loan_id,HttpSession session) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("status","Congrats");
		return new ResponseEntity<>(body, HttpStatus.OK);	
	}
	





}
