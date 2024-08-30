package com.example.sale_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class SaleManagementApplication {
	public static void main(String[] args) {
		SpringApplication.run(SaleManagementApplication.class, args);
	}

}
