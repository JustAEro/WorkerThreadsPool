package org.example.client;

import org.example.server.RequestExecutionResult;

import java.io.*;
import java.net.Socket;

public class Sender {
    public static void send(Request request) {

        final int port = Client.getPORT();
        final String hostname = Client.getHOSTNAME();

        final Socket clientSocket;
        try {
            clientSocket = new Socket(hostname, port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // get the output stream from the socket.
        OutputStream outputStream;
        try {
            outputStream = clientSocket.getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // create an object output stream from the output stream, so we can send an object through it
        ObjectOutputStream objectOutputStream;
        try {
            objectOutputStream = new ObjectOutputStream(outputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //send generated request to server
        try {
            objectOutputStream.writeObject(request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //receive response from server
        InputStream inputStream;
        try {
            inputStream = clientSocket.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ObjectInputStream objectInputStream;
        try {
            objectInputStream = new ObjectInputStream(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            RequestExecutionResult requestExecutionResult = (RequestExecutionResult) objectInputStream.readObject();
            if (requestExecutionResult.getResult() != request.getResult()) {
                throw new RuntimeException("Error! Wrong request was processed");
            }
            System.out.println(request + "\n" + requestExecutionResult + "\n");

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        //closing the socket after sending and getting server response
        try {
            clientSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
