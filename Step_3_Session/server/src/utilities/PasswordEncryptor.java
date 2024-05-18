package utilities;

import utility.ConsolePrinter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PasswordEncryptor {

    public static String hashPassword(String password) {
        try {
            String saltedPassword = password;
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = messageDigest.digest(saltedPassword.getBytes());
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : hashedBytes) {
                stringBuilder.append(String.format("%02x", b));
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException exception) {
            ConsolePrinter.printError("Password hashing algorithm not found!");
            throw new IllegalStateException();
        }
    }
}
