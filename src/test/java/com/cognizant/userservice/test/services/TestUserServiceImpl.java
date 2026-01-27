package com.cognizant.userservice.test.services;

import com.cognizant.userservice.dtos.UserDTO;
import com.cognizant.userservice.entities.User;
import com.cognizant.userservice.exceptions.EmailAlreadyExistsException;
import com.cognizant.userservice.repositories.UserRepository;
import com.cognizant.userservice.services.UserServiceImpl;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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

    private Validator validator;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        validator = Validation.buildDefaultValidatorFactory().getValidator();
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

        Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);

        assertThat(violations)
                .extracting(v -> v.getMessage())
                .anyMatch(msg -> msg.contains("User Name cannot be blank"));

    }

    @Test
    public void testCreateUserNegativeWhenUserNameLengthIsLess(){
        UserDTO userDTO=new UserDTO();
        userDTO.setUserName("ya");
        userDTO.setEmail("yash@example.com");

        Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);

        assertThat(violations)
                .extracting(v -> v.getMessage())
                .anyMatch(msg -> msg.contains("User Name must be between 3 to 50 characters"));
    }

    @Test
    public void testCreateUserNegativeWhenUserNameLengthIsMore(){
        UserDTO userDTO=new UserDTO();
        userDTO.setUserName("yashbiswarkarmaarunabhkalitaadrineelsahasurajsharmaakashpatil");
        userDTO.setEmail("yash@example.com");

        Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);

        assertThat(violations)
                .extracting(v -> v.getMessage())
                .anyMatch(msg -> msg.contains("User Name must be between 3 to 50 characters"));
    }

    @Test
    public void testCreateUserNegativeWhenEmailIsBlank(){
        UserDTO userDTO=new UserDTO();
        userDTO.setUserName("Yash");
        userDTO.setEmail("");

        Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);

        assertThat(violations)
                .extracting(v -> v.getMessage())
                .anyMatch(msg -> msg.contains("Email cannot be blank"));

    }

    @Test
    public void testCreateUserNegativeWhenEmailIsInValid(){
        UserDTO userDTO=new UserDTO();
        userDTO.setUserName("Yash");
        userDTO.setEmail("yashexamplecom");

        Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);

        assertThat(violations)
                .extracting(v -> v.getMessage())
                .anyMatch(msg -> msg.contains("Please enter a valid email"));

    }

    @Test
    public void testCreateUserException(){
        UserDTO userDTO=new UserDTO();
        userDTO.setUserName("Suraj");
        userDTO.setEmail("suraj@example.com");

        User user=new User();
        user.setUserName("Suraj");
        user.setEmail("suraj@example.com");

        try{
            when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));

            UserDTO actualUserDTO=userServiceImpl.createUser(userDTO);
        } catch(Exception ex){
            assertThat(ex)
                    .isInstanceOf(EmailAlreadyExistsException.class)
                    .hasMessageContaining("User Already Exists with Email Id: suraj@example.com");
        }
    }

    @Test
    public void testGetUserPositive(){
        try{
            User user=new User();
            user.setId(1L);
            user.setUserName("Aman");
            user.setEmail("Aman@example.com");

            UserDTO userDTO=new UserDTO();
            userDTO.setId(1L);
            userDTO.setUserName("Aman");
            userDTO.setEmail("Aman@example.com");

            when(userRepository.findById(any())).thenReturn(Optional.of(user));
            when(modelMapper.map(any(User.class),eq(UserDTO.class))).thenReturn(userDTO);

            UserDTO actualUserDTO=userServiceImpl.getUser(1L);
            assertNotNull(actualUserDTO);
        } catch(Exception ex){
            assertTrue(false);
        }

    }
}
