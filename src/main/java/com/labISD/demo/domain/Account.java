package com.labISD.demo.domain;

import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import jakarta.validation.constraints.Pattern;  
import lombok.Setter;
import com.labISD.demo.enums.ROLE;

@Entity
public class Account {
    
    @Id @Getter @Setter @NotNull(message = "id cannot be null")
    private UUID id;

    @NotNull(message = "username cannot be null") @Getter @Setter @Column(unique = true)
    @Size(min = 3, max = 30, message = "length of username must be 3-30") 
    @Pattern(regexp = "^[a-zA-Z0-9_-]{3,30}$", message = "username can only contain letters, numbers, hyphens, and underscores")
    private String username;

    @NotNull(message = "hashedPassword cannot be null") @Getter @Setter
    private String hashedPassword;

    @NotNull(message = "role cannot be null") @Getter @Setter
    private ROLE role;

    protected Account(){}

    public Account(UUID id, String username, String hashedPassword, ROLE role){
        this.id = id;
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.role = role;
    }

    @Override
    public String toString(){
        return String.format("Id: %s, Username: %s, HashedPassword: %s, Role: %s", id, username, hashedPassword, role);
    }
}
