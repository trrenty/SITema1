package httpcomunication;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class KeyManager {

    private List<URL> clients = new ArrayList<>();
    private String mode = null;

    public void init() {
        HttpServer server = null;
        try {
            server = HttpServer.create(new InetSocketAddress("localhost", 8001), 0);
            server.createContext("/connect", new ClientConnectionEstablisher(clients, mode));

            ExecutorService executor = Executors.newFixedThreadPool(2);

            server.setExecutor(executor);

            server.start();

            System.out.println(" Server started on port 8001");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        KeyManager km = new KeyManager();
        km.init();
    }
}
