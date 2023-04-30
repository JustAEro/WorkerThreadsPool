package org.example.client;

import org.example.server.Server;


public class Client {
    private static final int PORT = Server.getPort();
    private static final String HOSTNAME = "localhost";

    public static int getPORT() {
        return PORT;
    }

    public static String getHOSTNAME() {
        return HOSTNAME;
    }

    public static void main(String[] args) {
        int countOfGenerators = 10;
        RequestGenerator[] requestGenerators = new RequestGenerator[countOfGenerators];
        for (RequestGenerator requestGenerator: requestGenerators) {
            requestGenerator = new RequestGenerator(1);
        }
    }
}
