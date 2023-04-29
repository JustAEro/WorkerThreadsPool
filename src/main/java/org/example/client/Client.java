package org.example.client;

import org.example.server.RequestExecutionResult;
import org.example.server.Server;

import java.util.concurrent.LinkedBlockingQueue;

public class Client {
    public static void main(String[] args) {
        int numberOfRequestGenerators = 3;
        RequestGenerator[] requestGenerators = new RequestGenerator[numberOfRequestGenerators];
        for (RequestGenerator requestGenerator: requestGenerators) {
            requestGenerator = new RequestGenerator(1);
        }
    }
}
