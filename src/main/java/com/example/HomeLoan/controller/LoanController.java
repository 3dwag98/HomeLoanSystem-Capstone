package com.example.HomeLoan.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.HomeLoan.HomeLoanApplication;
import com.example.HomeLoan.model.LoanAccount;
import com.example.HomeLoan.model.SavingAccount;
import com.example.HomeLoan.model.Users;
import com.example.HomeLoan.repo.SavingAccountRepositiory;
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
		if(util.sessionCheck(session).getStatusCodeValue()==405)
			return new ResponseEntity<>("please login!", HttpStatus.METHOD_NOT_ALLOWED);
		int user_id = (int) session.getAttribute("user_id");
		Map<String, Object> body = new LinkedHashMap<>();
		logger.info("createLoanAccount applyForLoan> "+loanAcc);

		double salary  = loanAcc.getSalary();
		double loanAmt  = loanAcc.getAmount();

		 //check eligible
		if(loanAccService.isLoanEligible(salary,loanAmt)) {
			LoanAccount acc = loanAccService.createLoanAccount(loanAcc,user_id);
			body.put("status","Congrats");
			body.put("Loan Acc", acc);
			return new ResponseEntity<>(body, HttpStatus.OK);
		}
		else
		{
			double eligibleAmt = loanAccService.calculateEligibleLoanAmt(salary);
			loanAcc.setAmount(eligibleAmt);
			body.put("status","Pending");
			body.put("Loan Acc", loanAcc);
			return new ResponseEntity<>(body, HttpStatus.OK);			
		}		
		
	}
	
	@RequestMapping(value = {"/viewloan/{loan_id}"}, method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public ResponseEntity<?> viewForLoan(@PathVariable int loan_id,HttpSession session) {
		if(util.sessionCheck(session).getStatusCodeValue()==405)
			return new ResponseEntity<>("please login!", HttpStatus.METHOD_NOT_ALLOWED);
		int user_id = (int) session.getAttribute("user_id");
		Map<String, Object> body = new LinkedHashMap<>();
		LoanAccount loanAcc = loanAccService.getLoanDetails(loan_id);
		logger.info("createLoanAccount viewForLoan> "+loanAcc.toString());
		if (loanAcc!= null) {
			logger.info("loanAcc.getAccountNo(): "+loanAcc.getAccountNo());
			Optional<Users> user = userRepository.findById(user_id);
			if(user.get()!= null) {
				logger.info("loanAcc.user.get() "+user.get());
				SavingAccount savAcc = savingAccountService.findBysequenceIdAndUser(loanAcc.getAccountNo(), user.get());
				if(savAcc!= null) {}
					body.put("Loan Acc", loanAcc);
			}else {
				body.put("message","No loan Account has been found with this loan no and user");
			}

		}else {
			body.put("message","No loan Account has been found with this loan no and user");
		}
		return new ResponseEntity<>(body, HttpStatus.OK);
	}
	





}
