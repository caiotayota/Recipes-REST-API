package com.caiotayota.recipes.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
public class User {
    
    @Id
    @Pattern(regexp = ".+@.+\\..+", message = "Email is not valid.")
    private String email;
    
    @NotBlank
    @Size(min = 8, message = "The password must contain at least 8 characters.")
    private String password;
    
}
