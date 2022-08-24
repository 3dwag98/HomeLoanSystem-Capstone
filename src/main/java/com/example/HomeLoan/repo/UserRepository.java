package com.example.HomeLoan.repo;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.HomeLoan.model.Users;


public interface UserRepository extends JpaRepository<Users, Integer>  {

	public Users findByEmail(String emailId);

}
