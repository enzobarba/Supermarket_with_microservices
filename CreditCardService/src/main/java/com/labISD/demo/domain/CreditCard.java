package com.labISD.demo.domain;

import java.util.UUID;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Entity
public class CreditCard {
    
    @Id @GeneratedValue(strategy = GenerationType.UUID) @Getter
    private UUID cardId;

    @NotNull(message = "User ID cannot be null") @Getter @Setter
    private UUID userId;

    @NotNull(message = "Number cannot be null") @Getter @Setter
    @Pattern(regexp = "^[0-9]{16}$", message = "Number must be exactly 16 digits")
    @Size(min = 1, max = 50, message = "Length of number must be 1-30") 
    @Column(unique = true)
    private String number;

    @NotNull(message = "Type cannot be null") @Getter @Setter
    @Size(min = 1, max = 50, message = "Length of type must be 1-30") 
    private String type;

    @NotNull(message = "Expiration date cannot be null") @Getter @Setter
    @Pattern(regexp = "^(0[1-9]|1[0-2])/[0-9]{2}$", message = "Expiration date must be in the format MM/yy")
    private String expirationDate;

    @Getter @Setter
    @Min(value = 0, message = "Money must be greater than or equal to 0")
    private float money;

    protected CreditCard(){}

    public CreditCard(UUID userId, String number, String type, String expirationDate, float money){
        this.userId = userId;
        this.number = number;
        this.type = type;
        this.expirationDate = expirationDate;
        this.money = money;
    }

    @Override
    public String toString(){
        return String.format("Number: %s, Type: %s, Expiration Date: %s, Money: %.2f EUR", number, type, expirationDate, money);
    }

    public void spendMoney(float amount){
        this.money-= amount;
    }

    public boolean canSpendMoney(float amount){
        if(amount <= this.money){
            return true;
        }
        return false;
    }
}
