package com.example.HomeLoan.repo;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.example.HomeLoan.model.LoanAccount;
import com.example.HomeLoan.model.SavingAccount;
import com.example.HomeLoan.model.Users;

@Repository
public interface SavingAccountRepositiory extends JpaRepository<SavingAccount, Integer> {

	// SavingAccount findbyUser(Users user);
	
	@Query(value="SELECT r FROM SavingAccount r WHERE r.user.userId = :userid")
	List<SavingAccount> findSavingAccByUserid(int userid);


	@Query("SELECT a FROM SavingAccount a WHERE a.user.userId = :userid")
	SavingAccount findSavingAccountByUserid(@Param("userid") Integer userid);

	

	List<SavingAccount> findSavingAccountByUser(int userid);
	
	SavingAccount findByAccountno(Long accountno);
	
	//Count of Rows code
	@Query(value="SELECT count(sequenceId) FROM SavingAccount r WHERE r.user.userId = :userid")
	public long  countofSA(long userid);

	SavingAccount findByAccountnoAndUser(int accountno, Users users);


	SavingAccount findBysequenceId(int sequenceId) ;
	SavingAccount findBysequenceIdAndUser(int sequenceId, Users users) ;

}
