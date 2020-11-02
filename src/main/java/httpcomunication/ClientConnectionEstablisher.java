package httpcomunication;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ClientConnectionEstablisher implements HttpHandler {
    private List<URL> clients;
    private String mode;
    public ClientConnectionEstablisher(List<URL> clients, String mode) {
        this.clients = clients;
        this.mode = mode;
    }


    public void setMode (String mode) {
        if (this.mode == null) {
            this.mode = mode;
        }
        else if (!this.mode.equals(mode)) {
            this.mode = (ThreadLocalRandom.current().nextBoolean()) ? "ECB" : "OFB";
        }
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String msg = null;
        if("GET".equals(httpExchange.getRequestMethod())) {

        }else if("POST".equals(httpExchange.getRequestMethod())) {
            if (clients.size() >= 2) {
                msg = "fuck off";
            }
            else {
                msg = handlePostRequest(httpExchange);
            }
        }
        handleResponse(httpExchange, msg);
        System.out.println("Current Mode: " + mode);
    }

    private String handleGetRequest(HttpExchange httpExchange) {
        return null;
    }

    private String handlePostRequest(HttpExchange httpExchange) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(httpExchange.getRequestBody()));
        StringBuilder message = new StringBuilder();
        String partialMessage;
        try {
            while ((partialMessage = reader.readLine()) != null) {
                message.append(partialMessage);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject json = new JSONObject(message.toString());
        try {
            clients.add(new URL("http", json.get("ip").toString(), Integer.parseInt(json.get("port").toString()), ""));
            setMode(json.get("mode").toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        System.out.println(clients.get(clients.size() - 1).toString());

        if (clients.size() == 2) {

        }

        json.put("msg", "am primit");

        return json.toString();
    }

    private void sendMessage(String message, URL ... urls) {
        HttpURLConnection connection = null;
        for (URL client :
                urls) {
            try {
                connection = (HttpURLConnection)  client.openConnection();
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

    private void handleResponse(HttpExchange httpExchange, String response)  {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(httpExchange.getResponseBody()));

        try {
            writer.write(response);
            httpExchange.sendResponseHeaders(200, response.length());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
