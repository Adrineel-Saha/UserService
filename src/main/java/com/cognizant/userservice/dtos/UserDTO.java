package com.cognizant.userservice.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Schema(
        description = "UserDTO model information"
)
@Data
public class UserDTO {
    @Schema(
            description = "User Id"
    )
    private Long id;

    @NotBlank(message="User Name cannot be blank")
    @Size(min = 3, max = 50, message = "User Name must be between 3 to 50 characters")
    @Schema(
            description = "User Name"
    )
    private String userName;

    @Email(message="Please enter a valid email")
    @NotBlank(message="Email cannot be blank")
    @Schema(
            description= "User Email Address"
    )
    private String email;

}
