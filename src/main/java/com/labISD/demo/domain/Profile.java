package com.labISD.demo.domain;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Profile {
    
    @Id @NotNull(message = "id cannot be null") @Getter @Setter
    private UUID id;

    @NotNull(message = "name cannot be null") @Getter @Setter
    @Size(min = 1, max = 50, message = "length of name must be 1-50") 
    private String name;

    @NotNull(message = "surname cannot be null") @Getter @Setter
    @Size(min = 1, max = 50, message = "length of surname must be 1-50") 
    private String surname;

    @Email (message = "must be email format [name@domain.com]")
    @Getter @Setter @Column(unique = true) @NotNull(message = "email cannot be null")
    private String email;

    protected Profile(){}

    public Profile(UUID id, String name, String surname, String email){
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        //TO DO: ADD ADDRESS, HOUSENUMBER, CITY (AFTER CHAIN CLIENT-SERVER AND REQUEST)
    }

    @Override
    public String toString(){
        return String.format("ID: %s, Name: %s, Surname: %s, Email: %s", id, name, surname, email);
    }

}
