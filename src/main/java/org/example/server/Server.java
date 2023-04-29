package org.example.server;

import org.example.client.Request;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class Server {
    // Define the port on which the server listens
    private static final int port = 7777;

    // Create the queue to hold requests
    private static final LinkedBlockingQueue<Request> queueRequests = new LinkedBlockingQueue<>();
    private static final LinkedBlockingQueue<RequestExecutionResult> queueResults = new LinkedBlockingQueue<>();
    public static LinkedBlockingQueue<RequestExecutionResult> getQueueResults() {
        return queueResults;
    }
    public static int getPort() {
        return port;
    }

    public static void main(String[] args) {
        // Define the maximum number of worker threads
        final int maxNumberOfWorkerThreads;

        System.out.println("Enter pool size (max number of worker threads):");
        Scanner scanner = new Scanner(System.in);
        try {
            maxNumberOfWorkerThreads = scanner.nextInt();
            if (maxNumberOfWorkerThreads <= 0) {
                throw new RuntimeException("Number of worker threads can't be negative or zero");
            }
        }
        catch (InputMismatchException e){
            System.out.println(e.getMessage());
            return;
        }

        // Create the server socket
        //ServerSocket serverSocket;
        try (ServerSocket serverSocket = new ServerSocket(port)){

            // Create the executor service to handle worker threads
            try (ExecutorService executorService = Executors.newFixedThreadPool(maxNumberOfWorkerThreads)){
                // Create the server loop
                while (true) {
                    try {
                        // Wait for a client connection
                        Socket clientSocket = serverSocket.accept();

                        // Create a new request object from the client connection
                        // get the input stream from the connected socket
                        InputStream inputStream = clientSocket.getInputStream();
                        // create a DataInputStream, so we can read data from it.
                        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                        Request request = (Request) objectInputStream.readObject();
                        //Request request = new Request(clientSocket);
                        // Add the request to the queue
                        queueRequests.add(request);
                        // Submit a new worker thread to process the request
                        executorService.submit(new Worker(queueRequests));
                    }
                    catch (Exception e){
                        System.out.println(e.getMessage());
                        break;
                    }
                }
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}

