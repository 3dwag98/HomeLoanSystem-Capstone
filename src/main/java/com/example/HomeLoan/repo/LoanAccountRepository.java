package com.example.HomeLoan.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.HomeLoan.model.LoanAccount;
@Repository
public interface LoanAccountRepository extends JpaRepository<LoanAccount, Integer> {
	
	List<LoanAccount> findLoanAccountByAccountNo(Long accountNo);
	LoanAccount findByLoanAccId(int loanAccId); 
	
	@Query("SELECT COUNT(u) FROM LoanAccount u")
    long countRows();

}

