package com.example.HomeLoan.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table(name = "loan_account")

public class LoanAccount {
	
	@Id
	@Column(name = "loan_acc_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer loanAccId;
	

	@Column(name = "saving_acc_no")
	private int accountNo;
	
	@Override
	public String toString() {
		return "LoanAccount [loanAccId=" + loanAccId + ", accountNo=" + accountNo + ", amount=" + amount
				+ ", interestRate=" + interestRate + ", salary=" + salary + ", year=" + year + ", month=" + month
				+ ", address=" + address + ", status=" + status + "]";
	}

	@Column(name="loan_amount")
	private Double amount;
	
	@Column(name="interest_rate")
	private Double interestRate;
	
	@Column(name="salary")
	private Double salary;
	
	@Column(name="year")
	private int year; 
	
	@Column(name="month")
	private int month; 
	
	@Lob 
	private String address;
	
	@Column(name="status")
	private String status;

	
	
	public LoanAccount() {
		super();
	}


	public LoanAccount(Integer loanAccId, int accountNo, Double amount, Double interestRate, Double salary, int year,
			int month, String address, String status) {
		super();
		this.loanAccId = loanAccId;
		this.accountNo = accountNo;
		this.amount = amount;
		this.interestRate = interestRate;
		this.salary = salary;
		this.year = year;
		this.month = month;
		this.address = address;
		this.status = status;
	}


	public Integer getLoanAccId() {
		return loanAccId;
	}

	public void setLoanAccId(Integer loanAccId) {
		this.loanAccId = loanAccId;
	}

	public int getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(int string) {
		this.accountNo = string;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}



	public int getYear() {
		return year;
	}


	public void setYear(int year) {
		this.year = year;
	}


	public int getMonth() {
		return month;
	}


	public void setMonth(int month) {
		this.month = month;
	}


	public String getaddress() {
		return address;
	}

	public void seta(String address) {
		this.address = address;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


}
