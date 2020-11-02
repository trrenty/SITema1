import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enc Mode: ");
        String encMode = reader.readLine();
        System.out.println("Key: ");
        String key = reader.readLine();
        System.out.println("String to encode: ");
        String plaintext = reader.readLine();

        if (encMode.toLowerCase().trim().equals("ecb")) {
            String encrypted = AES.encryptECB(plaintext, key);
            String decrypted = AES.decryptECB(encrypted, key);
            System.out.println("Encrypted message: " + encrypted);
            System.out.println("Decrypted message: " + decrypted);
        }
        else if (encMode.toLowerCase().trim().equals("ofb")) {
            System.out.println("Enter iv: ");
            String iv = reader.readLine();
            String encrypted = AES.encryptOFB(plaintext, key, iv);
            String decrypted = AES.decryptOFB(encrypted, key, iv);
            System.out.println("Encrypted message: " + encrypted);
            System.out.println("Decrypted message: " + decrypted);

        }
        reader.close();
    }
}
