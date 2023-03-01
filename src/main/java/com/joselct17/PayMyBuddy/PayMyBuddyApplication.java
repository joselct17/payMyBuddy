package com.joselct17.PayMyBuddy;

import com.joselct17.PayMyBuddy.model.User;
import com.joselct17.PayMyBuddy.repository.UserRepository;
import com.joselct17.PayMyBuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@ComponentScan("com.joselct17.PayMyBuddy")
@EnableJpaRepositories("com.joselct17.PayMyBuddy.repository")
public class PayMyBuddyApplication extends SpringBootServletInitializer {

	@Autowired
	UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(PayMyBuddyApplication.class, args);
	}


}
