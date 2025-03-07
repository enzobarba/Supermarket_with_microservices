package com.labISD.demo.dto;
import lombok.Setter;
import lombok.Getter;

import java.util.UUID;

public class ProfileRequest {

    @Getter @Setter
    private UUID uuid;
    @Getter @Setter
    private String name;
    @Getter @Setter
    private String surname;
    @Getter @Setter
    private String email;

    public ProfileRequest(UUID uuid, String name, String surname, String email) {
        this.uuid = uuid;
        this.name = name;
        this.surname = surname;
        this.email = email;
    }
}
