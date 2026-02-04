package com.cognizant.userservice.test.repositories;

import com.cognizant.userservice.entities.User;
import com.cognizant.userservice.main.UserServiceApplication;
import com.cognizant.userservice.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

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
        user.setUserName("Adrineel");
        user.setEmail("Adrineel@example.com");

        entityManager.persist(user);

        List<User> userList = userRepository.findAll();
        assertTrue(userList.iterator().hasNext());
    }

    @Test
    public void testFindAllNegative(){
        List<User> userList = userRepository.findAll();
        assertTrue(!userList.iterator().hasNext());
    }

    @Test
    public void testFindByIdPositive(){
        User user=new User();
        user.setUserName("Akash");
        user.setEmail("Akash@example.com");

        entityManager.persist(user);
        Long id=user.getId();

        Optional<User> userOptional=userRepository.findById(id);
        assertTrue(userOptional.isPresent());
    }

    @Test
    public void testFindByIdNegative(){
        Optional<User> userOptional=userRepository.findById(2L);
        assertTrue(!userOptional.isPresent());
    }

    @Test
    public void testSavePositive(){
        User user=new User();
        user.setUserName("Yash");
        user.setEmail("Yash@example.com");

        userRepository.save(user);
        Long id=user.getId();

        Optional<User> userOptional=userRepository.findById(id);
        assertTrue(userOptional.isPresent());
    }

    @Test
    public void testSaveNegative(){
        Optional<User> userOptional=userRepository.findById(3L);
        assertTrue(!userOptional.isPresent());
    }

    @Test
    public void deletePositive(){
        User user=new User();
        user.setUserName("Arunabh");
        user.setEmail("Arunabh@example.com");

        entityManager.persist(user);
        Long id=user.getId();

        userRepository.delete(user);
        Optional<User> userOptional=userRepository.findById(id);
        assertTrue(!userOptional.isPresent());
    }

    @Test
    public void deleteNegative(){
        Optional<User> userOptional=userRepository.findById(4L);
        assertTrue(!userOptional.isPresent());
    }

    @Test
    public void findByEmailPositive(){
        User user=new User();
        user.setUserName("Suraj");
        user.setEmail("Suraj@example.com");

        entityManager.persist(user);
        String email=user.getEmail();

        Optional<User> userOptional=userRepository.findByEmail(email);
        assertTrue(userOptional.isPresent());
    }

    @Test
    public void findByEmailNegative(){
        Optional<User> userOptional=userRepository.findByEmail("Suraj@example.com");
        assertTrue(!userOptional.isPresent());
    }
}
