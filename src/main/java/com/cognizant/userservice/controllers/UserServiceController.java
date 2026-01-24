package com.cognizant.userservice.controllers;

import com.cognizant.userservice.dtos.UserDTO;
import com.cognizant.userservice.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
@Tag(
        name="CRUD REST APIs for User Service",
        description="CRUD REST APIs - Create User, Get User, Update User, Delete User"
)

public class UserServiceController {
    @Autowired
    private UserService userService;

    private static final Logger log = LoggerFactory.getLogger(UserServiceController.class);

    @PostMapping
    @Operation(
            summary="Create User REST API",
            description="Used to save a user to the database"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description="User Created"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description="Bad Request"
            )
    })
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO){
        log.info("Adding new User: " + userDTO);
        UserDTO responseUserDTO =userService.createUser(userDTO);
        log.info("Created User: " + responseUserDTO);
        return new ResponseEntity<>(responseUserDTO, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(
            summary="Get All Users REST API",
            description="Used to get all users from the database"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description="Users Found"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description="Bad Request"
            )
    })
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        log.info("Getting All Users");
        List<UserDTO> responseUserDTOList =userService.getAllUsers();
        log.info("Users list: " + responseUserDTOList);
        return new ResponseEntity<>(responseUserDTOList, HttpStatus.OK);
    }

    @GetMapping("{id}")
    @Operation(
            summary="Get User By Id REST API",
            description="Used to get a single user from the database"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description="User Found"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description="User Not Found"
            )
    })
    public ResponseEntity<UserDTO> getUser(@PathVariable("id") Long id){
        log.info("Getting User with Id: " + id);
        UserDTO responseUserDTO =userService.getUser(id);
        log.info("Found User: " + responseUserDTO);
        return new ResponseEntity<>(responseUserDTO, HttpStatus.OK);
    }

    @PutMapping("{id}")
    @Operation(
            summary="Update User REST API",
            description="Used to update a user in the database"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "202",
                    description="User Updated"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description="User Not Found"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description="Bad Request"
            )
    })
    public ResponseEntity<UserDTO> updateUser(@PathVariable("id") Long id,@Valid @RequestBody UserDTO userDTO){
        log.info("Updating User with Id: " + id + " and details: " + userDTO);
        UserDTO responseUserDTO =userService.updateUser(id,userDTO);
        log.info("Updated User: " + responseUserDTO);
        return new ResponseEntity<>(responseUserDTO, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("{id}")
    @Operation(
            summary="Delete User REST API",
            description="Used to delete a user from the database"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description="User Deleted"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description="User Not Found"
            ),
    })
    public ResponseEntity<String>  deleteUser(@PathVariable("id") Long id){
        log.info("Deleting User with Id: " + id);
        String result=userService.deleteUser(id);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
}
