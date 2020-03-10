package main.java.kamino.library;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {

    public String hashString(String input){
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(input.getBytes(StandardCharsets.UTF_8));
        byte[] digest = md.digest();
        return(String.format("%064x", new BigInteger(1, digest)));
    }

}
