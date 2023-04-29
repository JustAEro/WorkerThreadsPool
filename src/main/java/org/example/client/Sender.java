package org.example.client;

import org.example.server.Server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Sender {
    public static void send(Request request) {
        final Socket clientSocket;
        try {
            clientSocket = new Socket("localhost", Server.getPort());
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
            clientSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
