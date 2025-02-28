package threading;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ThreadMain {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8048);
        while(true) {
            Socket socket = serverSocket.accept();
            ExecutorFrameWork.handleClient(socket);
        }
    }

}
