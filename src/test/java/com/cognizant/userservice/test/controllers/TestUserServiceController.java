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

import static org.junit.jupiter.api.Assertions.assertTrue;
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
            List<UserDTO> actualCustomerDTOList=responseEntity.getBody();
            assertTrue(actualCustomerDTOList.size()>0);
        }catch(Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void testGetAllLocationsPositiveAssertStatusCode() {
        List<LocationDTO> locationDTOList=new ArrayList<>();

        LocationDTO locationDTO =new LocationDTO();
        locationDTO.setId(1);
        locationDTO.setName("Delhi");

        locationDTOList.add(locationDTO);

        try {
            when(locationService.getAllLocations()).thenReturn(locationDTOList);
            ResponseEntity<List<LocationDTO>> responseEntity=travelPlannerController.getAllLocations();
            assertEquals(200,responseEntity.getStatusCodeValue());
        }catch(Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void testGetAllLocationsNegativeAssertReturnValue() {
        List<LocationDTO> locationDTOList=new ArrayList<>();
        try {
            when(locationService.getAllLocations()).thenReturn(locationDTOList);
            ResponseEntity<List<LocationDTO>> responseEntity=travelPlannerController.getAllLocations();
            assertNull(responseEntity.getBody());
        }catch(Exception e) {
//            e.printStackTrace();
            assertTrue(false);
        }
    }

    @Test
    public void testGetAllLocationsNegativeAssertStatusCode() {
        List<LocationDTO> locationDTOList=new ArrayList<>();
        try {
            when(locationService.getAllLocations()).thenReturn(locationDTOList);
            ResponseEntity<List<LocationDTO>> responseEntity=travelPlannerController.getAllLocations();
            assertEquals(400,responseEntity.getStatusCodeValue());
        }catch(Exception e) {
            assertTrue(false);
        }
    }
}
