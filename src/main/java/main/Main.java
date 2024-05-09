package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) throws Exception {
        try (ServerSocket serverSocket = new ServerSocket(5050)) {
            System.out.println("Server started");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket);

                SocketThread socketThread = new SocketThread(clientSocket);
                socketThread.start();
            }
        }
    }


    private static class SocketThread extends Thread {
        private final Socket clientSocket;

        private SocketThread(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            System.out.println("Run: " + this.getName());
            try (
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
            ) {
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    out.println(inputLine);

                        if (inputLine.equals("Bye.")) {
                        break;
                    }
                }

                clientSocket.close();
                System.out.println("Client disconnected: " + clientSocket);

            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }
}