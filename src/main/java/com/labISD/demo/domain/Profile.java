package com.labISD.demo.domain;

import java.util.UUID;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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

    protected Profile(){}

    public Profile(UUID id, String name, String surname, float money){
        this.id = id;
        this.name = name;
        this.surname = surname;
        //TO DO: ADD ADDRESS, HOUSENUMBER, CITY (AFTER CHAIN CLIENT-SERVER AND REQUEST)
    }

    @Override
    public String toString(){
        return String.format("ID: %s, Name: %s, Surname: %s", id, name, surname);
    }

}
