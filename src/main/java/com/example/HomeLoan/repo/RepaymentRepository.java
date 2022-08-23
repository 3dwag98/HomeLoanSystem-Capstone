package com.example.HomeLoan.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.HomeLoan.model.Repayment;

import java.util.List;

public interface RepaymentRepository extends JpaRepository<Repayment, Integer> {
		
	Repayment findRepaymentByAccountNo(Long accountNo);
	
	List<Repayment> findRepaymentDetailsByAccountNo(int accountNo);
	
	
//	@Query(value="SELECT count(emi) FROM Repayment r WHERE r.loan_account_id = :accountNo AND r.status='paid'")
//	long NumbeOfEmi(@Param("accountNo") int accountNo);

//	@Query(value="SELECT count(*) FROM Repayment r WHERE r.accountNo = :loanAccId AND r.status='paid'")
//	public long  counByRecords(long loanAccId);
}

