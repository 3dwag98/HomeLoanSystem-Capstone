package com.example.HomeLoan.controller;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
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
import com.example.HomeLoan.model.SavingAccount;
import com.example.HomeLoan.model.Users;
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

	@RequestMapping(value = "/loanApplication", produces = "application/json",
	  		  method = {RequestMethod.GET})
	public ResponseEntity<?> getAccdetails(HttpSession session)
	{
		if(util.sessionCheck(session).getStatusCodeValue()==405)
			return new ResponseEntity<>("Login to continue with your loan application", HttpStatus.METHOD_NOT_ALLOWED);
		int user_id = (int) session.getAttribute("user_id");
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("prefilled details Acc", loanAccService.getLoanDetails(user_id));
		return new ResponseEntity<>(loanAccService.getLoanDetails(user_id), HttpStatus.OK);

	}
	
	@RequestMapping(value = {"/loanApplication","/loanApproval"}, method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public ResponseEntity<?> applyForLoan(@RequestBody @Valid  LoanAccount loanAcc ,HttpSession session) {
		logger.info(loanAcc.getAccountNo());
		if(util.sessionCheck(session).getStatusCodeValue()==405)
			return new ResponseEntity<>("Login to continue with your loan application", HttpStatus.METHOD_NOT_ALLOWED);
		int user_id = (int) session.getAttribute("user_id");
		Map<String, Object> body = new LinkedHashMap<>();
		logger.info("createLoanAccount applyForLoan> "+loanAcc);

		double salary  = loanAcc.getSalary();
		double loanAmt  = loanAcc.getAmount();

		if(loanAccService.loanEligibility(salary,loanAmt)) {
			LoanAccount acc = loanAccService.createLoanAccount(loanAcc,user_id);
			body.put("status","Congrats");
			body.put("Loan Acc", acc);
			return new ResponseEntity<>(body, HttpStatus.OK);
		}
		else
		{
			double eligibleAmt = loanAccService.maxAmountAsLoan(salary);
			loanAcc.setAmount(eligibleAmt);
			body.put("status","Pending");
			body.put("Loan Acc", loanAcc);
			return new ResponseEntity<>(body, HttpStatus.OK);			
		}		
		
	}
	
	@RequestMapping(value = {"/loanDetails/{loan_id}"}, method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public ResponseEntity<?> viewForLoan(@PathVariable int loan_id,HttpSession session) {
		if(util.sessionCheck(session).getStatusCodeValue()==405)
			return new ResponseEntity<>("Login to continue with your loan application", HttpStatus.METHOD_NOT_ALLOWED);
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
					body.put("Loan Account", loanAcc);
			}else {
				body.put("message","Sorry. There is no loan account found with this account number.");
			}
		}
		return new ResponseEntity<>(body, HttpStatus.OK);
	}
	





}
