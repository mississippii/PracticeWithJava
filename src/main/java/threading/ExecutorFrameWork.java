package threading;

import java.io.*;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorFrameWork {
    private static final int THREAD_POOL_SIZE = 10;
    private static final ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

    public static void handleClient(Socket socket) {
        executor.submit(() -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {


                String line;
                int contentLength = 0;
                while ((line = reader.readLine()) != null && !line.isEmpty()) {
                    if (line.startsWith("Content-Length:")) {
                        contentLength = Integer.parseInt(line.split(":")[1].trim());
                    }
                    System.out.println("Header: " + line);
                }

                char[] body = new char[contentLength];
                reader.read(body);
                String sms = new String(body).trim();
                System.out.println("Received SMS: " + sms);


                processSMS(sms);

                String responseBody = "SMS Received Successfully";
                writer.println("HTTP/1.1 200 OK");
                writer.println("Content-Type: text/plain");
                writer.println("Content-Length: " + responseBody.length());
                writer.println("Connection: close");
                writer.println();
                writer.println(responseBody);
                writer.flush();

                socket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    private static void insertIntoDatabase(String sms) {
        String url = "jdbc:mysql://localhost:3306/microservices";
        String user = "veer";
        String password = "1234";
        String query = "INSERT INTO sms_logs (message) VALUES (?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, sms);
            stmt.executeUpdate();
            System.out.println("Inserted into DB: " + sms);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void processSMS(String sms) {
        try {
            insertIntoDatabase(sms);
            sendToAnotherServer(sms);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void sendToAnotherServer(String sms) {
        System.out.println("Response: " + sms);
    }
}
