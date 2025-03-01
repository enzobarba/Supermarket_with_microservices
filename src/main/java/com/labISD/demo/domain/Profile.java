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
    @Size(min = 1, max = 50, message = "length of name must be 1-50") 
    private String name;

    @NotNull(message = "surname cannot be null") @Getter @Setter
    @Size(min = 1, max = 50, message = "length of surname must be 1-50") 
    private String surname;

    @Getter @Setter
    @Min(value = 0, message = "money must be greater than or equal to 0")
    private float money;

    protected Profile(){}

    public Profile(UUID id, String name, String surname, float money){
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.money = money;
        //TO DO: ADD ADDRESS, HOUSENUMBER, CITY (AFTER CHAIN CLIENT-SERVER AND REQUEST)
    }

    @Override
    public String toString(){
        return String.format("Name: %s, Surname: %s, Money: %.2f", name, surname, money);
    }

    public boolean canSpend(float amount){
        if(this.money >= amount){
            return true;
        }
        return false;
    }

    public void addMoney(int amount){
        this.money+= amount;
    }

    public void spendMoney(int amount){
        this.money-= amount;
    }
}
