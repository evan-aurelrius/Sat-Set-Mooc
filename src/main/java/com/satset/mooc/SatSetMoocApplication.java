package com.satset.mooc;

import com.satset.mooc.service.InitializeSqlDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SatSetMoocApplication {

	@Autowired
	InitializeSqlDataService initializeSqlDataService;

	public static void main(String[] args) {
		SpringApplication.run(SatSetMoocApplication.class, args);
	}

	@Bean
	public CommandLineRunner initializeSqlData() {
		return args -> initializeSqlDataService.quickInitialize();
	}

}
