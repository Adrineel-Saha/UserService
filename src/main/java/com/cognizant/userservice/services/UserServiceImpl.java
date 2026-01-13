package com.cognizant.userservice.services;

import com.cognizant.userservice.dtos.UserDTO;
import com.cognizant.userservice.entities.User;
import com.cognizant.userservice.exceptions.EmailAlreadyExistsException;
import com.cognizant.userservice.exceptions.ResourceNotFoundException;
import com.cognizant.userservice.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        if(userRepository.findByEmail(userDTO.getEmail()).isPresent()){
            throw new EmailAlreadyExistsException("User Already Exists with Email Id: " + userDTO.getEmail());
        }

        User user=modelMapper.map(userDTO,User.class);
        User savedUser=userRepository.save(user);
        UserDTO savedUserDTO = modelMapper.map(savedUser,UserDTO.class);
        return savedUserDTO;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> userList=userRepository.findAll();
        List<UserDTO> userDTOList=userList.stream()
                .map(user->modelMapper.map(user,UserDTO.class)).toList();

        if(userDTOList.isEmpty()){
            throw new RuntimeException("User List is Empty");
        }

        return userDTOList;
    }

    @Override
    public UserDTO getUser(Long id) {
        User user=userRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("User not found with id: "+ id)
        );

        UserDTO userDTO=modelMapper.map(user,UserDTO.class);
        return userDTO;
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user=userRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("User not found with id: " + id)
        );

        user.setUserName(userDTO.getUserName());
        user.setEmail(userDTO.getEmail());

        User updateUser=userRepository.save(user);

        UserDTO updatedUserDTO=modelMapper.map(updateUser, UserDTO.class);
        return updatedUserDTO;
    }

    @Override
    public String deleteUser(Long id) {
        User user=userRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("User not found with id: " + id)
        );

        userRepository.delete(user);
        return "User deleted with id: " + id;
    }
}
