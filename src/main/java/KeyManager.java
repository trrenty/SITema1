import java.io.IOError;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class KeyManager {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        try (ServerSocket serverSocket = new ServerSocket(6969)) {
            System.out.println("Server is listening to 6969: ");
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Hello client!");
                executor.submit(new ClientHandler(socket));
            }

        }
        catch (IOException ex) {
            System.out.println("Server exception: " + ex);
            ex.printStackTrace();
        }
    }




}
