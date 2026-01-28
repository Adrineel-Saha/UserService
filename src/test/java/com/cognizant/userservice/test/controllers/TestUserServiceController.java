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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes= UserServiceApplication.class)
public class TestUserServiceController {
    @Mock
    private UserService userService;
    @InjectMocks
    private UserServiceController userServiceController;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {

    }

    @Test
    public void testGetAllUsersPositiveAssertReturnValue() {
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
    public void testGetAllUsersPositiveAssertStatusCode() {
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
    public void testGetAllUsersNegativeAssertReturnValue() {
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
    public void testGetAllLUsersNegativeAssertStatusCode() {
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
    public void testDeleteUserPositiveAssertReturnValue() {
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
    public void testGetDeletePositiveAssertStatusCode() {
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
    public void testDeleteUserNegativeAssertReturnValue() {
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
    public void testGetLUserNegativeAssertStatusCode() {
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
    public void testGetUserPositiveAssertReturnValue() {
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
    public void testGetUserPositiveAssertStatusCode() {
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
    public void testGetUserNegativeAssertReturnValue() {
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
    public void testDeleteLUserNegativeAssertStatusCode() {
        UserDTO userDTO=null;
        try {
            when(userService.getUser(any())).thenReturn(userDTO);
            ResponseEntity<UserDTO> responseEntity=userServiceController.getUser(1L);
            assertEquals(400,responseEntity.getStatusCode().value());
        }catch(Exception e) {
            assertTrue(false);
        }
    }
}
