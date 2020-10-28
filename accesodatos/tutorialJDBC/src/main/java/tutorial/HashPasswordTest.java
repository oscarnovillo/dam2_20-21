package tutorial;

import lombok.SneakyThrows;

import java.security.MessageDigest;
import java.security.SecureRandom;

public class HashPasswordTest {

    @SneakyThrows
    public static void main(String[] args) {

        MessageDigest md = MessageDigest.getInstance("SHA3-512");

        String pass = "admin";

        byte[] hashBytes = md.digest(pass.getBytes());
        var hash1 = bytesToHex(hashBytes);

        pass="root";
        md.update(pass.getBytes());
        byte[] hashBytes1 = md.digest();
        String hash2 = bytesToHex(hashBytes1);

        pass="hola";
        md.update(pass.getBytes());
        String hash3 = bytesToHex(md.digest());

        System.out.println(hash1);
        System.out.println(hash2);
        System.out.println(hash3);
        System.out.println(hash3.equals(hash2));
        System.out.println(hash3.equals(hash1));


    }

    private static String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

}
