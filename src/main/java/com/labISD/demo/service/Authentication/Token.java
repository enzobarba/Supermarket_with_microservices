package com.labISD.demo.service.Authentication;

import java.security.Signature;
import java.time.Instant;

public record Token(String payload, Instant starting, Instant ending, 
                    String user, String producer, Signature signat) {
}

