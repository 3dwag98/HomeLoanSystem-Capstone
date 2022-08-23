package com.example.HomeLoan.service;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.HomeLoan.model.LoanAccount;
import com.example.HomeLoan.model.LoanRepayment;
import com.example.HomeLoan.model.LoanRepayment;
import com.example.HomeLoan.model.SavingAccount;
import com.example.HomeLoan.model.Users;
import com.example.HomeLoan.repo.LoanAccountRepository;
//import com.example.HomeLoan.repo.LoanRepayScheduleRepository;
//import com.example.HomeLoan.repo.LoanScheduleRepository;
import com.example.HomeLoan.repo.RepaymentRepository;
import com.example.HomeLoan.repo.SavingAccountRepositiory;

@Service
public class LoanRepaymentService {
	private static final Logger logger = LogManager.getLogger(LoanRepaymentService.class);

	@Autowired
	private LoanAccountRepository loanaccountRepo;

	@Autowired
	private UserService userService;


	@Autowired
	private SavingAccountRepositiory savingAccRepo;


	@Autowired
	private EmailService emailService;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private RepaymentRepository loanRepaymentRepo;


	public List<LoanAccount> getLoanAccounts() {
		return loanaccountRepo.findAll();
	}


	public LoanAccount getLoanAccountById(int id) {
//		LoanAccount LoanAccountdetails = loanaccountRepo.findById(LoanId);
		return loanaccountRepo.findByLoanAccId(id);
	}

	public List<LoanRepayment> getLoanSchedulebyID(int LoanId) {
		return loanRepaymentRepo.findRepaymentDetailsByAccountNo(LoanId);
	}

	public String updateRepayment(int LoanId) {

		List<LoanRepayment> existingPayment = loanRepaymentRepo.findRepaymentDetailsByAccountNo(LoanId);
//		int count = 0;
		int count = 0;
		for (LoanRepayment product : existingPayment) {
			logger.info(product.getStatus());
			if (product.getStatus().equalsIgnoreCase("paid")) {
				++count;
			}
		}

		logger.info("-----------" + count + "-----------------");
		if (count >= 3) {

			logger.info("----------------------------");

			for (LoanRepayment product : existingPayment) {
				product.setOutstanding(0.0);
				product.setStatus("paid");
				loanRepaymentRepo.save(product);
				logger.info("-----------Upated-----------------");
			}

			return "Successfylly updated";
		} else {
			return "Paid EMIs is less than 3 months";
		}

//		return existingPayment;
	}

	public double calInterest(double outstanding, double mothlyRateOfInterest) {
		return outstanding * mothlyRateOfInterest;
	}

	public double calPaidPrinciple(double emi, double mInterest) {
		return emi - mInterest;
	}

	public double calMonthlyEmi(double principle, double mothlyRateOfInterest, double tenureInMonths) {
		return (principle * mothlyRateOfInterest * Math.pow(1 + mothlyRateOfInterest, tenureInMonths)) / (Math.pow(1 + mothlyRateOfInterest, tenureInMonths) - 1);
	}

	List<LoanRepayment> repaySchedule = new ArrayList<LoanRepayment>();

	public Date addMonths(Date date, int months) {
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
		String s = DATE_FORMAT.format(date);
		LocalDate localdate = LocalDate.parse(s);
		LocalDate newDate = localdate.plusMonths(months);
		System.out.println("New Date : " + newDate);
		Date date_new = Date.from(newDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		return date_new;
	}


	public List<LoanRepayment> generateRepaymentSchedule(Date currdate, double principleAmount, double interestRate, double year, double month) {
		LoanRepaymentService pesObj = new LoanRepaymentService();
		logger.info("Entere generateRepaymentSchedule");
		double p = principleAmount;
		double r = interestRate * 0.01 / 12;
		double t = (year * 12) + month;
		double emi = pesObj.calMonthlyEmi(p, r, t);        //emi = (p*r*Math.pow(1+r,t))/(Math.pow(1+r,t)-1);

		double outstanding = p;


		for (int i = 0; i < t + 1; i++) {
			double mInterest = pesObj.calInterest(outstanding, r);//rate is monthly //= outstanding * r;
			double paidPrinciple = pesObj.calPaidPrinciple(emi, mInterest);//emi - mInterest;
			outstanding = outstanding - paidPrinciple;

			LoanRepayment obj = new LoanRepayment();
			int monthly_inc = 1;

			obj.setOutstanding(outstanding);
			obj.setInterest(mInterest);
			obj.setPrinciple(paidPrinciple);
			obj.setEmi(emi);


			Date new_date = pesObj.addMonths(currdate, monthly_inc + i);
			obj.setDate(new_date);

			logger.info(emi + " | " + mInterest + " | " + paidPrinciple + " | " + outstanding);

			repaySchedule.add(obj);
		}
		return repaySchedule;
	}

	public String updateEMIDetails(int loanaccountno, Double amount, int user_id) {
		Users user = userService.getUser(user_id).get();
		LoanRepaymentService pesObj = new LoanRepaymentService();

		LocalDate currentDate = LocalDate.now();
		int paidmonthscount = jdbcTemplate.queryForObject("select COUNT(*) from repayment where loan_account_id=? and date<=? and status='paid'", new Object[]{loanaccountno,currentDate}, Integer.class);
		logger.info("count" + paidmonthscount);
		logger.info("date" + currentDate);
		if (paidmonthscount >= 3) {

			String currentmonthemi = "select date,interest,principle,outstanding,emi from repayment where loan_account_id = ?  and status='paid' order by date DESC limit 1";
			LoanRepayment repayment = (LoanRepayment) jdbcTemplate.queryForObject(currentmonthemi, new Object[]{loanaccountno},
					new BeanPropertyRowMapper(LoanRepayment.class));

			System.out.println(repayment);
			Date emiDate = repayment.getDate();
			double outstanding = repayment.getOutstanding();
			double rate = 7.0 / 1200;
			int tenuremonths = loanaccountRepo.findByLoanAccId(loanaccountno).getYear() * 12 + loanaccountRepo.findByLoanAccId(loanaccountno).getMonth();
			double balance = outstanding - amount;//prepayment done
			int pendingmonths = tenuremonths - paidmonthscount;
			double newemi = (balance * rate * Math.pow(1 + rate, pendingmonths)) / (Math.pow(1 + rate, pendingmonths) - 1);
			for (int i = 0; i < pendingmonths + 1; i++) {
				double updatedInterest = balance * rate;
				double updatedPrinciple = newemi - updatedInterest;
				balance = balance - updatedPrinciple;
				System.out.println("emi:" + newemi + "interest:" + updatedInterest + "monthlyprinciple" + updatedPrinciple);

				int monthly_inc = 1;
				Date new_date = pesObj.addMonths(emiDate, monthly_inc + i);
				logger.info("new date:" + new_date);

				String updateSQL = "update repayment set emi=?,interest=?,outstanding=?,principle=? where date=now() and loan_account_id=? and status!='paid'";
				jdbcTemplate.update(updateSQL, newemi, updatedInterest, balance, updatedPrinciple, loanaccountno);
			}
			try {
				emailService.sendEmail(user.getEmail(), "Congrats, Prepayment done", "prepayment done", "batchpb2a@gmail.com");

			} catch (UnsupportedEncodingException | MessagingException e) {

				e.printStackTrace();
			}
			return "prepaymentdone,outstanding balance" + (outstanding - amount);
		} else {
			return "Cannot prepay ";
		}



	}

	public String emiPayment(int loanaccountno, int user_id) {

		LoanAccount loanAccount = loanaccountRepo.findByLoanAccId(loanaccountno);
		int saving_acc_no = loanAccount.getAccountNo();
		Users user = userService.getUser(user_id).get();

		logger.info("saving accountno:" + saving_acc_no);
//<<<<<<< HEAD
		SavingAccount userAccount = savingAccRepo.findBysequenceId(loanAccount.getAccountNo());
//=======
//logger.info("loanAccount"+loanAccount.getAccountNo());
		logger.info("loanAccount"+loanAccount.getLoanAccId());
		SavingAccount userAccount1 = savingAccRepo.findBysequenceId(loanAccount.getLoanAccId());
//>>>>>>> f1efb30fc6d8b10af8e342dbdd6a0c8eceaf4ab7
		logger.info("useraccount" + userAccount1);
		Double currentBalance = userAccount1.getBalance();


		String currentmonthemi = "select id as repaymentid,date,interest,principle,outstanding,emi from repayment where loan_account_id = ?  and status='Pending' order by date ASC limit 1";
		LoanRepayment repayment = (LoanRepayment) jdbcTemplate.queryForObject(currentmonthemi, new Object[]{loanaccountno},
				new BeanPropertyRowMapper(LoanRepayment.class));
		logger.info("repayment" + repayment.getRepaymentid());
		logger.info("loan acc id" + loanAccount.getLoanAccId());
		if (currentBalance > repayment.getEmi()) {
			Double balanceafterDeduction = currentBalance - repayment.getEmi();
			int repayId = repayment.getRepaymentid();

			jdbcTemplate.update(" update repayment set status='paid' where loan_account_id=? and id=? and updated_at = now() ", loanAccount.getLoanAccId(), repayId);
			jdbcTemplate.update("update savingaccount set curr_balance=? where acc_no=?", balanceafterDeduction, userAccount1.getAccountno());
		
			try {
				emailService.sendEmail(user.getEmail(), "Congrats, Your monthly EMI deducted", "EMI deducted", "batchpb2a@gmail.com");

			} catch (UnsupportedEncodingException | MessagingException e) {
				{


					e.printStackTrace();
				}

			}
			return "EMI deduct success";
		} else {
			return "not enough balance";
		}


	}


}


