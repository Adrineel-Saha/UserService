package com.cognizant.userservice.controllers;

import com.cognizant.userservice.dtos.UserDTO;
import com.cognizant.userservice.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserServiceController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO){
        UserDTO responseUserDTO =userService.createUser(userDTO);
        return new ResponseEntity<>(responseUserDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        List<UserDTO> responseUserDTOList =userService.getAllUsers();
        return new ResponseEntity<>(responseUserDTOList, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("id") Long id){
        UserDTO responseUserDTO =userService.getUser(id);
        return new ResponseEntity<>(responseUserDTO, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable("id") Long id,@Valid @RequestBody UserDTO userDTO){
        UserDTO responseUserDTO =userService.updateUser(id,userDTO);
        return new ResponseEntity<>(responseUserDTO, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String>  deleteUser(@PathVariable("id") Long id){
        String result=userService.deleteUser(id);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
}
