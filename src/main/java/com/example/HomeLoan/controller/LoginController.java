package com.example.HomeLoan.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.HomeLoan.model.AuthenticationDetails;
import com.example.HomeLoan.model.Users;
import com.example.HomeLoan.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class LoginController {
	@Autowired	
	UserService userService;
	
	
	@GetMapping(value="/")
	public ResponseEntity<?> home() {
		return new ResponseEntity<>("Welcome to Barclays bank", HttpStatus.CREATED);	
	}
	
	
	@PostMapping(value="/Users")
	public ResponseEntity<?> createUser(@Valid @RequestBody Users user) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("user_obj", userService.createUser(user));
		return new ResponseEntity<>(body, HttpStatus.CREATED);	
	}
		
	
	@GetMapping(value = "api/getuser/{userId}")
	public ResponseEntity<?> getUser(@Valid @PathVariable int userId) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("user_obj",userService.getUser(userId));
		return new ResponseEntity<>(body, HttpStatus.OK);
	}
	
	@PostMapping(value = "/login")
	public ResponseEntity<?> loginUser(@RequestBody AuthenticationDetails authenticationDetails, HttpSession session){
		Map<String, Object> body = new LinkedHashMap<>();
		return new ResponseEntity<>(userService.login(authenticationDetails, session), HttpStatus.OK);
	}
	
	@GetMapping(value = "/login")
	public ResponseEntity<?> loginUser(){
		return new ResponseEntity<>("Welcome to Barclays Bank login", HttpStatus.OK);
		
	}
	@PostMapping("/logout")
	public ResponseEntity<?>  destroySession(HttpServletRequest request) {
		request.getSession().invalidate();
		return new ResponseEntity<>("logout", HttpStatus.OK);
	}
	
	
	@PutMapping(value="/Users/{userId}")
	public ResponseEntity<?> updateUser(@Valid @RequestBody Users user) {
		userService.updateUser(user);
		return new ResponseEntity<>("User details updated succesfully", HttpStatus.OK);
		
	}
	
	@DeleteMapping(value = "Delete/{userId}")
	public ResponseEntity<?> deleteUser(@Valid @PathVariable Integer userId)
	
	{	
		userService.deleteUser(userId);
		return new ResponseEntity<>("User details deleted succesfully", HttpStatus.NO_CONTENT);
	}
	@GetMapping(value = "/allUser")

	public ResponseEntity<?>  getAllUser() {
		return new ResponseEntity<>(userService.getAllUser(), HttpStatus.OK);

	}



}
