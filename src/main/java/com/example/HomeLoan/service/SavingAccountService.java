package com.example.HomeLoan.service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.HomeLoan.model.SavingAccount;
import com.example.HomeLoan.model.Users;
import com.example.HomeLoan.repo.SavingAccountRepositiory;
import com.example.HomeLoan.repo.UserRepository;

@Service
public class SavingAccountService {

	@Autowired
	private SavingAccountRepositiory SavAccRepo;
	
	
	@Autowired
	private UserRepository userRepository;

	public SavingAccount saveBalance(SavingAccount savAccObj,HttpSession session) {
		int user_id = (int) session.getAttribute("user_id");
	    Random rnd = new Random();
	    int number = rnd.nextInt(999999);
	    savAccObj.setAccountno(number);
	    Optional<Users> user = userRepository.findById(user_id);
	    if(user.get()!=null) {
	    	savAccObj.setUser(user.get());
	    	
	    }  

		return SavAccRepo.save(savAccObj);	
	}
	
	public List<SavingAccount> findAccountByUserId(int id) {
		return SavAccRepo.findSavingAccountByUser(id);
	}
	public List<SavingAccount>  getAccDetails(Integer user_id) {

		return SavAccRepo.findSavingAccByUserid(user_id);
	}
	

	

	public SavingAccount findBysequenceIdAndUser(int i, Users users) {
	
		return SavAccRepo.findBysequenceIdAndUser(i, users);
	}

	

	


}
