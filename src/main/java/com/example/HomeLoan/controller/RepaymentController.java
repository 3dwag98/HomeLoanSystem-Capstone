package com.example.HomeLoan.controller;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.SqlResultSetMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.HomeLoan.model.Repayment;
import com.example.HomeLoan.service.LoanRepaymentService;

import com.example.HomeLoan.service.utility;



@RestController
public class RepaymentController {
@Autowired
private LoanRepaymentService repaymentService;

	@Autowired
	private utility util;

    @PutMapping("/foreclosure/{loanAccId}")   
    public ResponseEntity<?> foreclosure(@PathVariable int loanAccId,HttpSession session){  
		if(util.sessionCheck(session).getStatusCodeValue()==405)
			return new ResponseEntity<>("please login!", HttpStatus.METHOD_NOT_ALLOWED);
    	Map<String, Object> body = new LinkedHashMap<>();
		body.put("loanScheduleObject", repaymentService.updateRepayment(loanAccId));
		return new ResponseEntity<>(body, HttpStatus.OK);
    	
    }
}