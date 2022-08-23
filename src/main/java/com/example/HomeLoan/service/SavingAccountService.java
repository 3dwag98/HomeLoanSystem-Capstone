package com.example.HomeLoan.service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

import java.util.Random;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.HomeLoan.model.SavingAccount;
import com.example.HomeLoan.model.Users;
import com.example.HomeLoan.repo.SavingAccountRepositiory;
import com.example.HomeLoan.repo.UserRepository;
import com.example.HomeLoan.service.LoanAccountService;

@Service
public class SavingAccountService {

	@Autowired
	private SavingAccountRepositiory SavAccRepo;
	
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EmailService emailService;
	
	public SavingAccount saveBalance(SavingAccount savAccObj,HttpSession session) {
		int user_id = (int) session.getAttribute("user_id");
	    Random rnd = new Random();
	    int number = rnd.nextInt(999999);
	    savAccObj.setAccountno("BARCLAYS_"+String.valueOf(number));
	    Optional<Users> user = userRepository.findById(user_id);
	    if(user.get()!=null) {
	    	savAccObj.setUser(user.get());
	    	
	    }  
		try {
			emailService.sendEmail(user.get().getEmail(), "Congrats, Your Saving account has been created \n Your Accounnt id:BARCLAYS_"+String.valueOf(number), "Welcome to Barclays Bank", "batchpb2a@gmail.com");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
