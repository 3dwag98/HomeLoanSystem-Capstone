package com.example.HomeLoan;

//package com.example.HomeLoan.controller;


import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.HomeLoan.model.Users;
import com.example.HomeLoan.repo.RepaymentRepository;
import com.example.HomeLoan.repo.UserRepository;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)

//@RunWith(SpringJUnit4ClassRunner.class)
@RunWith(SpringRunner.class)
//@WebMvcTest(value = RegisterControllerTest.class)
@WebMvcTest(value = RegisterControllerTest.class,  excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class RegisterControllerTest {	
    
	@Autowired
    private MockMvc mockMvc;
	
    @Autowired
    private ObjectMapper mapper;  

    
    @MockBean
    private UserRepository userRepo;
    
    @MockBean
    private RepaymentRepository loanRepaymentRepo;
    
    Users RECORD_1 = new Users(1, "12345","p@11.com",Long.parseLong("89"));
//    Users RECORD_2 = new Users(2, "pass12356","user2@gmail.com",Long.parseLong("8796545433"));
//    Users RECORD_3 = new Users(3, "pass12356","user3@gmail.com",Long.parseLong("8796545434"));
    
//    @Test
//	 void contextLoads() {
//	 }
    
    List<Users> records = new ArrayList<>();
    
    @AfterEach
    public void insertMock_Records() {
     records.add(RECORD_1);
//     records.add(RECORD_2);
//     records.add(RECORD_3);
    }
	
    
    @Test
    public void getAllRecords_success() throws Exception {
    	
//    	mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/allUser")
//    		    .contentType("application/json"))
//    		    .andExpect(status().isOk());
    	
        Mockito.when(userRepo.findAll()).thenReturn(records);
//        When findAll called then ready with records (No DB calls) 
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/allUser")
                .contentType(MediaType.APPLICATION_JSON))
//        		.accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) //200
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].email", is("user1@gmail.com")));
    }
    
    
    @Test
    public void getLoanRepayment_success() throws Exception {
    	 
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/loanndetails/1/")
//                .contentType(MediaType.APPLICATION_JSON))
        		.accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) //200
//                .andExpect(jsonPath("$", hasSize(3)))
//                .andExpect(jsonPath("$[0].email", is("user1@gmail.com")))
                ;
                
    }
    

}