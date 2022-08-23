package com.example.HomeLoan;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import com.example.HomeLoan.service.LoanRepaymentService;

import springfox.documentation.swagger2.annotations.EnableSwagger2;


import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@EnableSwagger2
public class HomeLoanApplication {
	
	
	private static final Logger logger = LogManager.getLogger(HomeLoanApplication.class);
	public static void main(String[] args) {

        logger.info("Info log InSIde Home APP");
		SpringApplication.run(HomeLoanApplication.class, args);

	}
}
