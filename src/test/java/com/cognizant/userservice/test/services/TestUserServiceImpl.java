package com.cognizant.userservice.test.services;

import com.cognizant.userservice.dtos.UserDTO;
import com.cognizant.userservice.entities.User;
import com.cognizant.userservice.repositories.UserRepository;
import com.cognizant.userservice.services.UserServiceImpl;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;

public class TestUserServiceImpl {
    @Mock
    private UserRepository userRepository;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {

    }

    @Test
    public void testGetAllUsersPositiveOneUserFound(){
        try{
            List<User> listUserMock = mock(List.class);
            when(userRepository.findAll()).thenReturn(listUserMock);

            User user = mock(User.class);
            when(listUserMock.stream()).thenReturn(Stream.of(user));

            UserDTO userDTO = mock(UserDTO.class);
            when(modelMapper.map(any(User.class),eq(UserDTO.class))).thenReturn(userDTO);

            List<UserDTO> userDTOList = userServiceImpl.getAllUsers();
            assertTrue(userDTOList.size()==1);
        } catch(Exception ex){
            assertTrue(false);
        }
    }

    @Test
    public void testGetAllUsersPositiveMultipleUsersFound(){
        try{
            List<User> listUserMock = mock(List.class);
            when(userRepository.findAll()).thenReturn(listUserMock);

            User user1 = mock(User.class);
            User user2 = mock(User.class);
            when(listUserMock.stream()).thenReturn(Stream.of(user1,user2));

            UserDTO userDTO1 = mock(UserDTO.class);
            UserDTO userDTO2 = mock(UserDTO.class);
            when(modelMapper.map(any(User.class),eq(UserDTO.class))).thenReturn(userDTO1).thenReturn(userDTO2);

            List<UserDTO> userDTOList = userServiceImpl.getAllUsers();
            assertTrue(userDTOList.size()>1);
        } catch(Exception ex){
            assertTrue(false);
        }
    }

    @Test
    public void testGetAllUsersException(){
        try{
            when(userRepository.findAll()).thenReturn(List.of());

            List<UserDTO> userDTOList = userServiceImpl.getAllUsers();

        } catch(Exception ex){
            assertThatThrownBy(() -> userServiceImpl.getAllUsers())
                    .isInstanceOf(RuntimeException.class)
                    .hasMessage("User List is Empty");
        }
    }

    @Test
    public void testCreateUserPositive(){
        try{
            UserDTO userDTO=new UserDTO();
            userDTO.setUserName("Akash");
            userDTO.setEmail("akash@example.com");

            User user=new User();
            user.setUserName("Akash");
            user.setEmail("akash@example.com");

            User savedUser=new User();
            savedUser.setUserName("Akash");
            savedUser.setEmail("akash@example.com");

            UserDTO saveduserDTO=new UserDTO();
            saveduserDTO.setUserName("Akash");
            saveduserDTO.setEmail("akash@example.com");

            when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.empty());
            when(modelMapper.map(any(UserDTO.class),eq(User.class))).thenReturn(user);
            when(userRepository.save(any(User.class))).thenReturn(savedUser);
            when(modelMapper.map(any(User.class),eq(UserDTO.class))).thenReturn(saveduserDTO);

            UserDTO actualUserDTO=userServiceImpl.createUser(userDTO);
            assertNotNull(actualUserDTO);
        } catch(Exception ex){
            assertTrue(false);
        }
    }

    @Test
    public void testCreateUserNegativeWhenUserNameIsBlank(){
        UserDTO userDTO=new UserDTO();
        userDTO.setUserName("");
        userDTO.setEmail("yash@example.com");

        try{
            userServiceImpl.createUser(userDTO);
        } catch(Exception ex){
            assertThat(ex)
                    .isInstanceOf(ConstraintViolationException.class)
                    .hasMessageContaining("User Name Cannot be blank");

        }
    }

}
