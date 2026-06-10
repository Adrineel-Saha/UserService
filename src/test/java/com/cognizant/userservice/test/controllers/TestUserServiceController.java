package com.cognizant.userservice.test.controllers;

import com.cognizant.userservice.controllers.UserServiceController;
import com.cognizant.userservice.dtos.UserDTO;
import com.cognizant.userservice.main.UserServiceApplication;
import com.cognizant.userservice.services.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes= UserServiceApplication.class)
@ActiveProfiles("test")
public class TestUserServiceController {
    @Mock
    private UserService userService;
    @InjectMocks
    private UserServiceController userServiceController;

    @Autowired
    private LocalValidatorFactoryBean validator;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {

    }

    @Test
    void testGetAllUsersPositiveAssertReturnValue() {
        List<UserDTO> userDTOList=new ArrayList<>();

        UserDTO userDTO=new UserDTO();
        userDTO.setId(1L);
        userDTO.setUserName("Akash");
        userDTO.setEmail("akash@example.com");

        userDTOList.add(userDTO);

        try {
            when(userService.getAllUsers()).thenReturn(userDTOList);
            ResponseEntity<List<UserDTO>> responseEntity=userServiceController.getAllUsers();
            List<UserDTO> actualUserDTOList=responseEntity.getBody();
            assertTrue(actualUserDTOList.size()>0);
        }catch(Exception e) {
            assertTrue(false);
        }
    }

    @Test
    void testGetAllUsersPositiveAssertStatusCode() {
        List<UserDTO> userDTOList=new ArrayList<>();

        UserDTO userDTO=new UserDTO();
        userDTO.setId(1L);
        userDTO.setUserName("Akash");
        userDTO.setEmail("akash@example.com");

        userDTOList.add(userDTO);

        try {
            when(userService.getAllUsers()).thenReturn(userDTOList);
            ResponseEntity<List<UserDTO>> responseEntity=userServiceController.getAllUsers();
            assertEquals(200,responseEntity.getStatusCode().value());
        }catch(Exception e) {
            assertTrue(false);
        }
    }

    @Test
    void testGetAllUsersNegativeAssertReturnValue() {
        List<UserDTO> userDTOList=new ArrayList<>();
        try {
            when(userService.getAllUsers()).thenReturn(userDTOList);
            ResponseEntity<List<UserDTO>> responseEntity=userServiceController.getAllUsers();
            assertNull(responseEntity.getBody());
        }catch(Exception e) {
            assertTrue(false);
        }
    }

    @Test
    void testGetAllLUsersNegativeAssertStatusCode() {
        List<UserDTO> userDTOList=new ArrayList<>();
        try {
            when(userService.getAllUsers()).thenReturn(userDTOList);
            ResponseEntity<List<UserDTO>> responseEntity=userServiceController.getAllUsers();
            assertEquals(400,responseEntity.getStatusCode().value());
        }catch(Exception e) {
            assertTrue(false);
        }
    }

    @Test
    void testDeleteUserPositiveAssertReturnValue() {
        UserDTO userDTO=new UserDTO();
        userDTO.setId(1L);
        userDTO.setUserName("Aman");
        userDTO.setEmail("aman@example.com");

        try {
            when(userService.deleteUser(any())).thenReturn("User deleted with Id: " + userDTO.getId());
            ResponseEntity<String> responseEntity=userServiceController.deleteUser(1L);
            String result=responseEntity.getBody();
            assertNotNull(result);
        }catch(Exception e) {
            assertTrue(false);
        }
    }

    @Test
    void testGetDeletePositiveAssertStatusCode() {
        UserDTO userDTO=new UserDTO();
        userDTO.setId(1L);
        userDTO.setUserName("Aman");
        userDTO.setEmail("aman@example.com");

        try {
            when(userService.deleteUser(any())).thenReturn("User deleted with Id: " + userDTO.getId());
            ResponseEntity<String> responseEntity=userServiceController.deleteUser(1L);
            assertEquals(200,responseEntity.getStatusCode().value());
        }catch(Exception e) {
            assertTrue(false);
        }
    }

    @Test
    void testDeleteUserNegativeAssertReturnValue() {
        String result=null;
        try {
            when(userService.deleteUser(any())).thenReturn(result);
            ResponseEntity<String> responseEntity=userServiceController.deleteUser(1L);
            assertNull(responseEntity.getBody());
        }catch(Exception e) {
            assertTrue(false);
        }
    }

    @Test
    void testGetLUserNegativeAssertStatusCode() {
        UserDTO userDTO=null;
        try {
            when(userService.getUser(any())).thenReturn(userDTO);
            ResponseEntity<UserDTO> responseEntity=userServiceController.getUser(1L);
            assertEquals(400,responseEntity.getStatusCode().value());
        }catch(Exception e) {
            assertTrue(false);
        }
    }

    @Test
    void testGetUserPositiveAssertReturnValue() {
        UserDTO userDTO=new UserDTO();
        userDTO.setId(1L);
        userDTO.setUserName("Suraj");
        userDTO.setEmail("suraj@example.com");

        try {
            when(userService.getUser(any())).thenReturn(userDTO);
            ResponseEntity<UserDTO> responseEntity=userServiceController.getUser(1L);
            UserDTO actualUserDTO=responseEntity.getBody();
            assertNotNull(actualUserDTO);
        }catch(Exception e) {
            assertTrue(false);
        }
    }

    @Test
    void testGetUserPositiveAssertStatusCode() {
        UserDTO userDTO=new UserDTO();
        userDTO.setId(1L);
        userDTO.setUserName("Suraj");
        userDTO.setEmail("suraj@example.com");

        try {
            when(userService.getUser(any())).thenReturn(userDTO);
            ResponseEntity<UserDTO> responseEntity=userServiceController.getUser(1L);
            assertEquals(200,responseEntity.getStatusCode().value());
        }catch(Exception e) {
            assertTrue(false);
        }
    }

    @Test
    void testGetUserNegativeAssertReturnValue() {
        UserDTO userDTO=null;
        try {
            when(userService.getUser(any())).thenReturn(userDTO);
            ResponseEntity<UserDTO> responseEntity=userServiceController.getUser(1L);
            assertNull(responseEntity.getBody());
        }catch(Exception e) {
            assertTrue(false);
        }
    }

    @Test
    void testDeleteLUserNegativeAssertStatusCode() {
        UserDTO userDTO=null;
        try {
            when(userService.getUser(any())).thenReturn(userDTO);
            ResponseEntity<UserDTO> responseEntity=userServiceController.getUser(1L);
            assertEquals(400,responseEntity.getStatusCode().value());
        }catch(Exception e) {
            assertTrue(false);
        }
    }

    @Test
    void testAddUserWhenUserIsValid() {
        UserDTO userDTO=new UserDTO();
        userDTO.setId(1L);
        userDTO.setUserName("Suraj");
        userDTO.setEmail("suraj@example.com");

        validator.validate(userDTO).stream().forEach((constraintViolation)->assertNull(constraintViolation));
    }

    @Test
    void testAddUserPositiveAssertReturnValue() {
        UserDTO userDTO=new UserDTO();
        userDTO.setId(1L);
        userDTO.setUserName("Suraj");
        userDTO.setEmail("suraj@example.com");

        try {
            when(userService.createUser(any(UserDTO.class))).thenReturn(userDTO);
            ResponseEntity<UserDTO> responseEntity=userServiceController.createUser(userDTO);
            UserDTO actualUserDTO=responseEntity.getBody();
            assertNotNull(actualUserDTO);
        }catch(Exception e) {
            assertTrue(false);
        }
    }

    @Test
    void testAddUserPositiveAssertStatusCode() {
        UserDTO userDTO=new UserDTO();
        userDTO.setId(1L);
        userDTO.setUserName("Suraj");
        userDTO.setEmail("suraj@example.com");

        try {
            when(userService.createUser(any(UserDTO.class))).thenReturn(userDTO);
            ResponseEntity<UserDTO> responseEntity=userServiceController.createUser(userDTO);
            assertEquals(201,responseEntity.getStatusCode().value());
        }catch(Exception e) {
            assertTrue(false);
        }
    }

    @Test
    void testAddUserWhenUserIsNotValid() {
        UserDTO userDTO=new UserDTO();
        userDTO.setId(1L);
        userDTO.setUserName("S");
        userDTO.setEmail("surajexamplecom");

        validator.validate(userDTO).stream().forEach((constraintViolation)->assertNotNull(constraintViolation));
    }

    @Test
    void testAddUserNegativeAssertReturnValue() {
        UserDTO userDTO=null;

        try {
            when(userService.createUser(any(UserDTO.class))).thenReturn(userDTO);
            ResponseEntity<UserDTO> responseEntity=userServiceController.createUser(userDTO);
            UserDTO actualUserDTO=responseEntity.getBody();
            assertNull(actualUserDTO);
        }catch(Exception e) {
            assertTrue(false);
        }
    }

    @Test
    void testAddUserNegativeAssertStatusCode() {
        UserDTO userDTO=null;

        try {
            when(userService.createUser(any(UserDTO.class))).thenReturn(userDTO);
            ResponseEntity<UserDTO> responseEntity=userServiceController.createUser(userDTO);
            assertEquals(400,responseEntity.getStatusCode().value());
        }catch(Exception e) {
            assertTrue(false);
        }
    }

    @Test
    void testUpdateUserWhenUserIsValid() {
        UserDTO userDTO=new UserDTO();
        userDTO.setId(1L);
        userDTO.setUserName("Yash");
        userDTO.setEmail("yash@example.com");

        validator.validate(userDTO).stream().forEach((constraintViolation)->assertNull(constraintViolation));
    }

    @Test
    void testUpdateUserPositiveAssertReturnValue() {
        UserDTO userDTO=new UserDTO();
        userDTO.setId(1L);
        userDTO.setUserName("Yash");
        userDTO.setEmail("yash@example.com");

        try {
            when(userService.updateUser(any(),any(UserDTO.class))).thenReturn(userDTO);
            ResponseEntity<UserDTO> responseEntity=userServiceController.updateUser(1L,userDTO);
            UserDTO actualUserDTO=responseEntity.getBody();
            assertNotNull(actualUserDTO);
        }catch(Exception e) {
            assertTrue(false);
        }
    }

    @Test
    void testUpdateUserPositiveAssertStatusCode() {
        UserDTO userDTO=new UserDTO();
        userDTO.setId(1L);
        userDTO.setUserName("Yash");
        userDTO.setEmail("yash@example.com");

        try {
            when(userService.updateUser(any(),any(UserDTO.class))).thenReturn(userDTO);
            ResponseEntity<UserDTO> responseEntity=userServiceController.updateUser(1L,userDTO);
            assertEquals(202,responseEntity.getStatusCode().value());
        }catch(Exception e) {
            assertTrue(false);
        }
    }

    @Test
    void testUpdateUserWhenUserIsNotValid() {
        UserDTO userDTO=new UserDTO();
        userDTO.setId(1L);
        userDTO.setUserName("Y");
        userDTO.setEmail("yashexamplecom");

        validator.validate(userDTO).stream().forEach((constraintViolation)->assertNotNull(constraintViolation));
    }

    @Test
    void testUpdateUserNegativeAssertReturnValue() {
        UserDTO userDTO=null;

        try {
            when(userService.updateUser(any(),any(UserDTO.class))).thenReturn(userDTO);
            ResponseEntity<UserDTO> responseEntity=userServiceController.updateUser(1L,userDTO);
            UserDTO actualUserDTO=responseEntity.getBody();
            assertNull(actualUserDTO);
        }catch(Exception e) {
            assertTrue(false);
        }
    }

    @Test
    void testUpdateUserNegativeAssertStatusCode() {
        UserDTO userDTO=null;

        try {
            when(userService.updateUser(any(),any(UserDTO.class))).thenReturn(userDTO);
            ResponseEntity<UserDTO> responseEntity=userServiceController.updateUser(1L,userDTO);
            assertEquals(400,responseEntity.getStatusCode().value());
        }catch(Exception e) {
            assertTrue(false);
        }
    }

    @Test
    void testGetAllUsersPositiveAssertListSize() {
        List<UserDTO> userDTOList=new ArrayList<>();

        UserDTO userDTO1=new UserDTO();
        userDTO1.setId(1L);
        userDTO1.setUserName("Akash");
        userDTO1.setEmail("akash@example.com");

        UserDTO userDTO2=new UserDTO();
        userDTO2.setId(2L);
        userDTO2.setUserName("Suraj");
        userDTO2.setEmail("suraj@example.com");

        userDTOList.add(userDTO1);
        userDTOList.add(userDTO2);

        try {
            when(userService.getAllUsers()).thenReturn(userDTOList);
            ResponseEntity<List<UserDTO>> responseEntity=userServiceController.getAllUsers();
            assertEquals(2,responseEntity.getBody().size());
        }catch(Exception e) {
            assertTrue(false);
        }
    }

    @Test
    void testGetUserPositiveAssertUserName() {
        UserDTO userDTO=new UserDTO();
        userDTO.setId(1L);
        userDTO.setUserName("Suraj");
        userDTO.setEmail("suraj@example.com");

        try {
            when(userService.getUser(any())).thenReturn(userDTO);
            ResponseEntity<UserDTO> responseEntity=userServiceController.getUser(1L);
            assertEquals("Suraj",responseEntity.getBody().getUserName());
        }catch(Exception e) {
            assertTrue(false);
        }
    }

    @Test
    void testGetUserPositiveAssertEmail() {
        UserDTO userDTO=new UserDTO();
        userDTO.setId(1L);
        userDTO.setUserName("Suraj");
        userDTO.setEmail("suraj@example.com");

        try {
            when(userService.getUser(any())).thenReturn(userDTO);
            ResponseEntity<UserDTO> responseEntity=userServiceController.getUser(1L);
            assertEquals("suraj@example.com",responseEntity.getBody().getEmail());
        }catch(Exception e) {
            assertTrue(false);
        }
    }

    @Test
    void testDeleteUserPositiveAssertMessage() {
        try {
            when(userService.deleteUser(any())).thenReturn("User deleted with Id: 1");
            ResponseEntity<String> responseEntity=userServiceController.deleteUser(1L);
            assertEquals("User deleted with Id: 1",responseEntity.getBody());
        }catch(Exception e) {
            assertTrue(false);
        }
    }

    @Test
    void testAddUserPositiveAssertUserName() {
        UserDTO userDTO=new UserDTO();
        userDTO.setId(1L);
        userDTO.setUserName("Suraj");
        userDTO.setEmail("suraj@example.com");

        try {
            when(userService.createUser(any(UserDTO.class))).thenReturn(userDTO);
            ResponseEntity<UserDTO> responseEntity=userServiceController.createUser(userDTO);
            assertEquals("Suraj",responseEntity.getBody().getUserName());
        }catch(Exception e) {
            assertTrue(false);
        }
    }

    @Test
    void testUpdateUserPositiveAssertUpdatedUserName() {
        UserDTO userDTO=new UserDTO();
        userDTO.setId(1L);
        userDTO.setUserName("UpdatedYash");
        userDTO.setEmail("yash@example.com");

        try {
            when(userService.updateUser(any(),any(UserDTO.class))).thenReturn(userDTO);
            ResponseEntity<UserDTO> responseEntity=userServiceController.updateUser(1L,userDTO);
            assertEquals("UpdatedYash",responseEntity.getBody().getUserName());
        }catch(Exception e) {
            assertTrue(false);
        }
    }

}
