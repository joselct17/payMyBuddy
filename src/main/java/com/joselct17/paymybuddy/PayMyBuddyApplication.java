package com.joselct17.paymybuddy;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan({"com.joselct17.paymybuddy.service"})
@EntityScan({"com.joselct17.paymybuddy.model"})
public class PayMyBuddyApplication extends SpringBootServletInitializer {


	public static void main(String[] args) {
		SpringApplication.run(PayMyBuddyApplication.class, args);
	}


}
