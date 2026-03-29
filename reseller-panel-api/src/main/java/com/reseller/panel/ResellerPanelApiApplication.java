package com.reseller.panel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ResellerPanelApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResellerPanelApiApplication.class, args);
		
		System.out.println("Welcome to Reseller API's");
	}

}
