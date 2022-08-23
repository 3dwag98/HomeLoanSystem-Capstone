
package com.example.HomeLoan.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.example.HomeLoan.model.LoanRepayment;
import com.example.HomeLoan.service.LoanAccountService;
import com.example.HomeLoan.service.LoanRepaymentService;
import com.example.HomeLoan.service.utility;


@RequestMapping("/loan/repayment")
@RestController
public class LoanRepaymentController {


	@Autowired
	private LoanRepaymentService loanservice;

	@Autowired
	private LoanAccountService loanAccountService;
	
	@Autowired
	private utility util;

	@GetMapping(value = "/loanscheduler")
	public ResponseEntity<?> findLoandetais(HttpSession session) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("status","Congrats");
		return new ResponseEntity<>(body, HttpStatus.OK);	
	}

	@PostMapping(value = "/loanscheduler/{id}/")
	public ResponseEntity<?> findLoanSchedulebyID(@Valid @PathVariable int id,HttpSession session) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("status","Congrats");
		return new ResponseEntity<>(body, HttpStatus.OK);	
	}

	@PostMapping(value = "/loanndetails/{id}/")
	public ResponseEntity<?> findLoandetaisbyID(@Valid @PathVariable int id,HttpSession session) {	
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("status","Congrats");
		return new ResponseEntity<>(body, HttpStatus.OK);	
	}

	@PostMapping("/loanndetails/csvexport/{loanid}")
    public void exportToCSV(@Valid @PathVariable int loanid,HttpServletResponse response,HttpSession session) throws IOException {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("status","Congrats");
		
    }
    @PostMapping("/prepayment/{loanaccountno}")
    public ResponseEntity<? >  updateEMIDetails(@PathVariable int loanaccountno,@RequestBody Double partialPaymentAmount,HttpSession session) {
    	Map<String, Object> body = new LinkedHashMap<>();
		body.put("status","Congrats");
		return new ResponseEntity<>(body, HttpStatus.OK);	
    }

    @PutMapping("/emipayment/{loanaccountno}")
    public String updateMonthlyEMIPayment(@PathVariable int loanaccountno,HttpSession session)
    {
    	Map<String, Object> body = new LinkedHashMap<>();
		body.put("status","Congrats");
		return "check";	

    }

}
