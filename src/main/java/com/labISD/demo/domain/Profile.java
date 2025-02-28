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
    
    @Id @Getter @Setter
    private UUID id;

    @NotNull @Getter @Setter @Size(min = 2, max = 20)
    private String name;

    @NotNull @Getter @Setter @Size(min = 2, max = 20)
    private String surname;

    @NotNull @Getter @Setter @Min(0)
    private float money;

    protected Profile(){}

    public Profile(String name, String surname, float money){
        this.name = name;
        this.surname = surname;
        this.money = money;
    }

    @Override
    public String toString(){
        return "Name: " + name + ", Surname: " + surname + ", money: " + money;
    }
}
