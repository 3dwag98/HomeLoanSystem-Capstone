package com.example.HomeLoan.repo;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.HomeLoan.model.LoanRepayment;

public interface RepaymentRepository extends JpaRepository<LoanRepayment, Integer> {
		
	LoanRepayment findRepaymentByAccountNo(Long accountNo);
	
	List<LoanRepayment> findRepaymentDetailsByAccountNo(int accountNo);

}

