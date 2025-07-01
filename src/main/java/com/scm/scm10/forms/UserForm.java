package com.scm.scm10.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString

public class UserForm {
    @NotBlank(message = "Username is required")
    @Size(min=3, message="Minimun 3 characters required")
    private String name;
    
    @Email(message = "Invalid email address")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min=3, message="Minimun 6 characters required")
    private String password;

    @NotBlank(message = "About is required")
    private String about;

    @Size(min=8, max=12, message = "Invalid phone number")
    private String phoneNumber;
}
