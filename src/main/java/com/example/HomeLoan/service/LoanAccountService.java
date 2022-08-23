package com.example.HomeLoan.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.HomeLoan.model.LoanAccount;
import com.example.HomeLoan.model.LoanRepayment;
import com.example.HomeLoan.model.SavingAccount;
import com.example.HomeLoan.model.Users;
import com.example.HomeLoan.repo.LoanAccountRepository;
import com.example.HomeLoan.repo.RepaymentRepository;
import com.example.HomeLoan.repo.SavingAccountRepositiory;
@Service
public class LoanAccountService {
	
	
	private static final Logger logger = LogManager.getLogger(LoanAccountService.class);
	
	@Autowired
	private LoanAccountRepository loanAccrepo;
	
	
	@Autowired
	private SavingAccountRepositiory savingAccRepo;
	
	@Autowired
	private RepaymentRepository paymentRepo;
	
	@Autowired
	private SavingAccountService savingAccountService;
	
	@Autowired
	private LoanRepaymentService loanPayService;
	
	@Autowired
	private UserService userService;
	
	private final int batchSize = 30;
	
	@Autowired
	private EmailService emailService;

	
	
	
	public LoanAccount saveAppliedLoan( LoanAccount obj) {

		return loanAccrepo.save(obj);
	}
	
	public LoanAccount getLoanDetails(Integer id) {

		return loanAccrepo.findByLoanAccId(id);
	}
	

	public  double calculateEligibleLoanAmt(double salary) {
		return salary * 50;
	}

	public boolean isLoanEligible(double salary, double loanAmt) {
		return salary * 50 >= loanAmt;
	}

	public LoanAccount createLoanAccount(LoanAccount loanAcc, int user_id) {
		SavingAccount userAccount = savingAccRepo.findBysequenceId(loanAcc.getAccountNo());
		logger.info("createLoanAccount service> "+loanAcc.getAccountNo());
		loanAcc.setAccountNo(userAccount.getSequenceId());
		loanAcc.setStatus("Approved");
		loanAcc = loanAccrepo.save(loanAcc);
		Users user = userService.getUser(user_id).get();
		try {
			emailService.sendEmail(user.getEmail(), "Congrats, Your Loan has been Approoved\n Your Accounnt id:"+loanAcc.getLoanAccId(), "Loan Accepted", "batchpb2a@gmail.com");
			populatePaymentDBforNewUser(loanAcc);
		} catch (UnsupportedEncodingException | MessagingException e) {

			e.printStackTrace();
		}
		return loanAcc;
		
	}
	
	
	@Async
	public String populatePaymentDBforNewUser(LoanAccount loanAcc) {
		List<LoanRepayment> paymentSchedulePerUserList = loanPayService.generateRepaymentSchedule(new java.sql.Date(System.currentTimeMillis()), loanAcc.getAmount(), loanAcc.getInterestRate(), loanAcc.getYear(), loanAcc.getMonth());
		paymentSchedulePerUserList.forEach(payment -> {
		    payment.setAccountNo(loanAcc.getLoanAccId());
		    payment.setStatus("Pending");	    
		});
		for (int i = 0; i < paymentSchedulePerUserList.size(); i = i + batchSize) {
			if( i+ batchSize > paymentSchedulePerUserList.size()){
				List<LoanRepayment> paymenttbatch = paymentSchedulePerUserList.subList(i, paymentSchedulePerUserList.size() - 1);
				paymentRepo.saveAll(paymenttbatch);
			break;
			}
			List<LoanRepayment> paymenttbatch = paymentSchedulePerUserList.subList(i, i + batchSize);
			paymentRepo.saveAll(paymenttbatch);
		}
		return "Success";
	}


	
}
