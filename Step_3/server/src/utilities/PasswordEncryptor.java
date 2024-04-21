package utilities;

import utility.ConsolePrinter;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncryptor {
    public static String hashPassword(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            byte[] bytes = messageDigest.digest(password.getBytes());
            BigInteger integer = new BigInteger(1,bytes);
            String newPassword = integer.toString(16);
            while (newPassword.length() < 32) {
                newPassword = "0" + newPassword;
            }
            return newPassword;
        } catch (NoSuchAlgorithmException exception) {
            ConsolePrinter.printError("Password hashing algorithm not found!");
            throw new IllegalStateException();
        }
    }
}
