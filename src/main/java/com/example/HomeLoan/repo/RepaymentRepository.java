package com.example.HomeLoan.repo;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.HomeLoan.model.Repayment;

public interface RepaymentRepository extends JpaRepository<Repayment, Integer> {
		
	Repayment findRepaymentByAccountNo(Long accountNo);
	
	List<Repayment> findRepaymentDetailsByAccountNo(int accountNo);

}

