package httpcomunication;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class ClientHttp {
    public static void main(String[] args) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(new URL("http://localhost:8001/"), "connect/").openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");

            connection.setDoOutput(true);
            connection.setDoInput(true);
            PrintWriter wr = new PrintWriter(connection.getOutputStream());

            wr.print("{ \" msg\": 10}");
            wr.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
