package com.cognizant.userservice.test.repositories;

import com.cognizant.userservice.entities.User;
import com.cognizant.userservice.main.UserServiceApplication;
import com.cognizant.userservice.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;

@DataJpaTest
@ContextConfiguration(classes = UserServiceApplication.class)
public class TestUserRepository {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testFindAllPositive(){
        User user=new User();
    }
}
