package com.example.HomeLoan.service;


import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import com.example.HomeLoan.model.AuthenticationDetails;
import com.example.HomeLoan.model.Users;



public interface UserService {

	public Users createUser(Users user);
	
	public Optional<Users> getUser(int userId);
	
	public void updateUser(Users user);
	
	
	public List<Users> getAllUser();

	public void deleteUser(Integer userId);
	
	public String login(AuthenticationDetails authenticationDetails, HttpSession session);



}
