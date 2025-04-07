package com.labISD.demo.domain;

import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import jakarta.validation.constraints.Pattern;  
import lombok.Setter;
import jakarta.validation.constraints.Email;
import com.labISD.demo.enums.ROLE;

@Entity
public class Account {
    
    @Id @Getter @Setter @NotNull(message = "ID cannot be null") @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull(message = "username cannot be null") @Getter @Setter @Column(unique = true)
    @Size(min = 3, max = 30, message = "length of username must be 3-30") 
    @Pattern(regexp = "^[a-zA-Z0-9_-]{3,30}$", message = "username can only contain letters, numbers, hyphens, and underscores")
    private String username;

    @NotNull(message = "hashedPassword cannot be null") @Getter @Setter
    private String hashedPassword;

    @NotNull(message = "role cannot be null") @Getter @Setter
    private ROLE role;

    @NotNull(message = "name cannot be null") @Getter @Setter
    @Size(min = 1, max = 50, message = "length of name must be 1-50") 
    private String name;

    @NotNull(message = "surname cannot be null") @Getter @Setter
    @Size(min = 1, max = 50, message = "length of surname must be 1-50") 
    private String surname;

    @Email (message = "must be email format [name@domain.com]")
    @Getter @Setter @Column(unique = true) @NotNull(message = "email cannot be null")
    private String email;

    protected Account(){}

    public Account(String username, String hashedPassword, ROLE role, String name, String surname, String email){
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.role = role;
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    @Override
    public String toString(){
        return String.format("Username: %s, Role: %s, Name: %s, Surname: %s, Email: %s", username, role, name, surname, email);
    }
}
