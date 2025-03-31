package com.labISD.demo.Authentication;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TokenStore {
    private List<Token> tokens;

    @Autowired
    private TokenGenerator generator;

    public TokenStore() {
        tokens = new ArrayList<>();
    }

    public void store(Token t) {
        tokens.add(t);
    }

    public void delete(String payload) {
        tokens.removeIf(t -> t.payload().equals(payload));
    }

    public boolean isPresent(String id) {
        return tokens.stream()
                .anyMatch(t -> t.payload().equals(id) && Instant.now().isBefore(t.ending()));
    }

    public String findUser(String id) {
        return tokens.stream()
                .filter(t -> t.payload().equals(id) && Instant.now().isBefore(t.ending()))
                .findAny()
                .map(t -> t.user())
                .orElse("user unknown");
    }

    public boolean isValidSignature(String id) {
        Optional<Token> o = tokens.stream()
                .filter(t -> t.payload().equals(id)).findAny();
        if (o.isEmpty()){
            return false;
        }
        return isValidSignature(o.get());
    }

    public boolean isValidSignature(Token t) {
        boolean verif = false;
        try {
            verif = generator.verifySign(t.signat(), t.payload());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return verif;
    }
}
