package com.cognizant.userservice.test.main;

import com.cognizant.userservice.controllers.UserServiceController;
import com.cognizant.userservice.main.UserServiceApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes= UserServiceApplication.class)
class UserServiceApplicationTests {
	@Autowired
	private UserServiceController userServiceController;

	@Test
	void contextLoads() {
		assertNotNull(userServiceController);
	}

}
