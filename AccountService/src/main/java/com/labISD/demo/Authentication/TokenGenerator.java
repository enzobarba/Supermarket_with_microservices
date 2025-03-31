package com.labISD.demo.Authentication;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.springframework.stereotype.Component;

@Component
public class TokenGenerator {

    private static final String producer = "MyTokenAuthority";
    private static final String allChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz";
    private static final int validity = 4;
    private static final int tokenLength = 30;
    private static KeyPair keyPair;

    static {
        try {
            keyPair = getKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating RSA keys", e);
        }
    }

    public Token tokenBuild(final String user) {
        if (keyPair == null) return null;

        String payload = getPayload();
        Signature signature = signIt(payload);
        if (signature == null) return null;

        return new Token(payload, Instant.now(), Instant.now().plus(validity, ChronoUnit.HOURS),
                user, producer, signature);
    }

    private String getPayload() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < tokenLength; i++) {
            int index = (int) (allChars.length() * Math.random());
            result.append(allChars.charAt(index));
        }
        return result.toString();
    }

    private Signature signIt(String payload) {
        try {
            Signature signature = Signature.getInstance("SHA1WithRSA");
            signature.initSign(keyPair.getPrivate());
            signature.update(payload.getBytes(StandardCharsets.UTF_8));
            return signature;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean verifySign(Signature sig, String payload) {
        if (keyPair == null || sig == null) return false;
        try {
            byte[] text = payload.getBytes(StandardCharsets.UTF_8);

            Signature verifier = Signature.getInstance("SHA1WithRSA");
            verifier.initVerify(keyPair.getPublic());
            verifier.update(text);

            return verifier.verify(sig.sign());  
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static KeyPair getKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(1024);
        return kpg.generateKeyPair();
    }
}
