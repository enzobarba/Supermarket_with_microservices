package com.labISD.demo.domain;

import java.util.UUID;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Future;
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

    @NotNull(message = "id cannot be null") @Getter @Setter
    private UUID profileId;

    @NotNull(message = "number cannot be null") @Getter @Setter
    @Pattern(regexp = "^[0-9]{16}$", message = "number must be exactly 16 digits")
    @Size(min = 1, max = 50, message = "length of number must be 1-30") 
    private String number;

    @NotNull(message = "type cannot be null") @Getter @Setter
    @Size(min = 1, max = 50, message = "length of type must be 1-30") 
    private String type;

    @NotNull(message = "expiration date cannot be null") @Getter @Setter
    @Future
    @Pattern(regexp = "^(0[1-9]|1[0-2])/[0-9]{2}$", message = "Expiration date must be in the format MM/yy")
    private String expirationDate;

    @Getter @Setter
    @Min(value = 0, message = "money must be greater than or equal to 0")
    private float money;

    protected CreditCard(){}

    public CreditCard(UUID profileId, String number, String type, String expirationDate, float money){
        this.profileId = profileId;
        this.number = number;
        this.type = type;
        this.expirationDate = expirationDate;
        this.money = money;
    }

    @Override
    public String toString(){
        return String.format("Card ID: %s, Profile ID: %s, Number: %s, Type: %s, Expiration Date: %s, Money: %.2f", cardId, profileId, number, type, expirationDate,money);
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
