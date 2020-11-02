

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket socket;

    public ClientHandler (Socket socket) {
        this.socket = socket;
    }
    @Override
    public void run() {

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            System.out.println(Thread.currentThread().getName() + ": " + socket.getRemoteSocketAddress().toString());
            while (true) {
                String clientMessage = reader.readLine();
                System.out.println(Thread.currentThread().getName() + ": " + clientMessage);
                if (clientMessage.trim().toLowerCase().equals("exit")) {
                    reader.close();
                    writer.close();
                    socket.close();
                    System.out.println("Farewell, " + Thread.currentThread().getName());
                    return;
                }
                writer.println("I dont agree with: " + clientMessage);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
