package com.cognizant.userservice.services;

import com.cognizant.userservice.dtos.UserDTO;
import com.cognizant.userservice.entities.User;
import com.cognizant.userservice.exceptions.EmailAlreadyExistsException;
import com.cognizant.userservice.exceptions.ResourceNotFoundException;
import com.cognizant.userservice.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
//    @Autowired
//    private KafkaTemplate<String, UserDTO> kafkaTemplate;
//    @Value("${app.kafka.userproducer.topic}")
//    private String topic;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        if(userRepository.findByEmail(userDTO.getEmail()).isPresent()){
            throw new EmailAlreadyExistsException("User Already Exists with Email Id: " + userDTO.getEmail());
        }

        User user=modelMapper.map(userDTO,User.class);
        User savedUser=userRepository.save(user);
        UserDTO savedUserDTO = modelMapper.map(savedUser,UserDTO.class);

//        kafkaTemplate.send(topic, savedUserDTO);
//        log.info("Published UserDTO to {}: {}", topic, savedUserDTO);

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
                ()->new ResourceNotFoundException("User not found with Id: "+ id)
        );

        UserDTO userDTO=modelMapper.map(user,UserDTO.class);

//        kafkaTemplate.send(topic, userDTO);
//        log.info("Published UserDTO to {}: {}", topic, userDTO);

        return userDTO;
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user=userRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("User not found with Id: " + id)
        );

        if(userRepository.findByEmail(userDTO.getEmail()).isPresent()){
            throw new EmailAlreadyExistsException("User Already Exists with Email Id: " + userDTO.getEmail());
        }

        user.setUserName(userDTO.getUserName());
        user.setEmail(userDTO.getEmail());

        User updateUser=userRepository.save(user);
        UserDTO updatedUserDTO=modelMapper.map(updateUser, UserDTO.class);

//        kafkaTemplate.send(topic, updatedUserDTO);
//        log.info("Published UserDTO to {}: {}", topic, updatedUserDTO);

        return updatedUserDTO;
    }

    @Override
    public String deleteUser(Long id) {
        User user=userRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("User not found with Id: " + id)
        );

        log.info("Deleted User: " + user);

        userRepository.delete(user);
        return "User deleted with Id: " + id;
    }
}
