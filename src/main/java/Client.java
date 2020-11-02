import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 6969)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader keyboardReader = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                System.out.println("Input: ");
                String message = keyboardReader.readLine();
                writer.println(message);
                message = reader.readLine();
                System.out.println("Server: " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
