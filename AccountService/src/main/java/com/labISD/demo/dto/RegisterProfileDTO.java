package com.labISD.demo.dto;

import java.util.UUID;

public record RegisterProfileDTO(UUID id, String name, String surname, String email) {
}

