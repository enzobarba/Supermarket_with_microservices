package com.labISD.demo.domain;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

enum ROLE{
    purchaser,
    supplyer
}

@Entity
public class Account {
    
    @Id @Getter @Setter
    private UUID id;

    @NotNull @Size(min = 2, max = 20) @Getter @Setter
    private String username;

    @NotNull @Getter @Setter
    private String hashedPassword;

    @NotNull @Getter @Setter
    private String salt;

    @Email @Getter @Setter
    private String email;

    @NotNull @Getter @Setter
    private ROLE role;

    protected Account(){}

    public Account(String username, String hashedPassword, String salt, String email, String name, String surname, ROLE role, float money){
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.salt = salt;
        this.email = email;
        this.role = role;
    }

    @Override
    public String toString(){
        return "Username: " + username + ", hashedPassword: " + hashedPassword + ", Salt: " + salt + ", Email: " + email + ", Role: " + role;
    }
}
