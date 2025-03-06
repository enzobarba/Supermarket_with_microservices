package com.labISD.demo.service.Authentication;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.springframework.stereotype.Component;

@Component
public class TokenGenerator {

    private static final String producer = "MyTokenAuthority";
    private static final String allChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz";
    private static final int validity = 10;
    private static final int tokenLength = 30;
    private static KeyPair keyPair = null;

    public Token tokenBuild(final String user) {
        if (keyPair == null)
            try {
                keyPair = getKeyPair();
            } catch (Exception e) {
            }
        if (keyPair == null)
            return null;
        String payload = getPayload();
        Signature signat = null;
        try {
            signat = signIt(payload);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Token t = new Token(payload, Instant.now(), Instant.now().plus(validity, ChronoUnit.SECONDS),
                user, producer, signat);
        return t;
    }

    private String getPayload() {
        int index;
        String result = "";
        for (int i = 0; i < tokenLength; i++) {
            index = (int) (allChars.length() * Math.random());
            result = result.concat(allChars.substring(index, index + 1));
        }
        return result;
    }

    private Signature signIt(String payload) throws Exception {
        byte[] text = payload.getBytes("UTF8");
        Signature s = Signature.getInstance("SHA1WithRSA");
        s.initSign(keyPair.getPrivate());
        s.update(text);
        return s;
    }

    public boolean verifySign(Signature sig, String payload) throws Exception {
        if (keyPair == null)
            return false;
        byte[] signatureBytes = sig.sign();
        sig.initVerify(keyPair.getPublic());
        byte[] text = payload.getBytes("UTF8");
        sig.update(text);
        return sig.verify(signatureBytes);
    }

    private KeyPair getKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(1024);
        return kpg.genKeyPair();
    }
}
