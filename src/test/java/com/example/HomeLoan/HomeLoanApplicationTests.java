package com.example.HomeLoan;

import org.apache.catalina.startup.ClassLoaderFactory.Repository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.HomeLoan.model.LoanAccount;
import com.example.HomeLoan.model.Repayment;
import com.example.HomeLoan.repo.LoanAccountRepository;
import com.example.HomeLoan.repo.RepaymentRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

@SpringBootTest
class HomeLoanApplicationTests {

	@Autowired
	LoanAccountRepository loanDetailsrepo;
	

	@Autowired
	RepaymentRepository repaymentrepo;
	
	
	@Test
	void contextLoads() {
		
	}
	
	@Test
	void testRepaymentdata() {
		List<Repayment> list = repaymentrepo.findRepaymentDetailsByAccountNo(1);
//		assertThat(list).size().isGreaterThan(0);		
		assertEquals(4,(list).size());
	}
	
//	void test() {
//		List<Repayment> list = repaymentrepo.findRepaymentDetailsByAccountNo(1);
////		assertThat(list).size().isGreaterThan(0);		
//		assertEquals(4,(list).size());
//	}

}
