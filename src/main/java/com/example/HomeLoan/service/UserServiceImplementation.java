package com.example.HomeLoan.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.HomeLoan.model.AuthenticationDetails;
import com.example.HomeLoan.model.Users;
import com.example.HomeLoan.repo.UserRepository;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class UserServiceImplementation implements UserService{
	
	@Autowired
	UserRepository userRepository;

	
	@Override
	public Users createUser(Users user) {
		// TODO Auto-generated method stub
		return userRepository.save(user);
	}

	@Override
	public Optional<Users> getUser(int userId) {
		return userRepository.findById(userId);
	}

	@Override
	public void updateUser(Users user) {
		// TODO Auto-generated method stub
	    userRepository.save(user);
	}

	

	@Override
	public List<Users> getAllUser() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

	@Override
	public void deleteUser(Integer userId) {
		// TODO Auto-generated method stub
		
		userRepository.deleteById(userId);
		
		
	}

	@Override
	public String login(AuthenticationDetails authenticationDetails,HttpSession session) {
		// TODO Auto-generated method stub
		Users user = userRepository.findByEmail(authenticationDetails.getEmailId());
		
		if(user != null) {
			if(user.getPassword().equals(authenticationDetails.getPassword())) {
				session.setAttribute("user_id", user.getUserId());
				return "user logged in and User ID is "+user.getUserId();
			}	
			else
				return "password did not match";
		}
		return "no such user found";
	}

	

	
	

}
