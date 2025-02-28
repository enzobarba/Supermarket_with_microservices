package com.labISD.demo.domain;

import java.util.UUID;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Profile {
    
    @Id @NotNull(message = "id cannot be null") @Getter @Setter
    private UUID id;

    @NotNull(message = "name cannot be null") @Getter @Setter
    @Size(min = 2, max = 20, message = "length of name must be 2-20") 
    private String name;

    @NotNull(message = "surname cannot be null") @Getter @Setter
    @Size(min = 2, max = 20, message = "length of surname must be 2-20") 
    private String surname;

    @NotNull(message = "money cannot be null") @Getter @Setter
    @Min(value = 0, message = "money must be greater than or equal to 0")
    private float money;

    protected Profile(){}

    public Profile(String name, String surname, float money){
        this.name = name;
        this.surname = surname;
        this.money = money;
    }

    @Override
    public String toString(){
        return String.format("Name: %s, Surname: %s, Money: %.2f", name, surname, money);
    }
}
