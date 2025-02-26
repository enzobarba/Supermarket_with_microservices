package com.labISD.demo;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.Setter;

enum ROLE{
    purchaser,
    supplyer
}

@Entity
public class User {
    
    @Id @GeneratedValue(strategy = GenerationType.AUTO) @Getter
    private Long id;

    @NotNull @Min(5) @Max(15) @Getter @Setter
    private String username;

    @NotNull @Getter @Setter
    private String hashedPassword;

    @NotNull @Getter @Setter
    private String salt;

    @Email @Getter @Setter
    private String email;

    @NotNull @Getter @Setter
    private String name;

    @NotNull @Getter @Setter
    private String surname;

    @NotNull @Getter @Setter
    private ROLE role;

    @Min(0) @Getter @Setter
    private float money;

}
