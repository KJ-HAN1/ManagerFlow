package com.example.managerFlow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class ManagerFlowApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManagerFlowApplication.class, args);
	}

}
