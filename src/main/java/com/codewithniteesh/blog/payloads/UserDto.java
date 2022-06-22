package com.codewithniteesh.blog.payloads;

import com.codewithniteesh.blog.entities.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private int id;

    @NotEmpty(message = "Name must NOT be empty !!!")
    @Size(min = 3, message = "Name must be of 3 character or more !!!")
    private String name;

    @Email(message = "Email is not valid !!!")
    private String email;


    @NotNull
    @Pattern(regexp = "^(?=.*[0-9])"
            + "(?=.*[a-z])(?=.*[A-Z])"
            + "(?=.*[@#$%^&+=])"
            + "(?=\\S+$).{8,20}$"
            , message = "Password must contains at least one lower case, upper case character, number, special" +
            "character, and length should be with 8-10 !!!") // regex for password
    private String password;

    @NotNull(message = "About field should not be blank !!!")
    private String about;

    private Set<RoleDto> roles = new HashSet<>();
}
