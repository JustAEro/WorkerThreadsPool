package org.example.server;

import org.example.client.Request;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.concurrent.LinkedBlockingQueue;

public class Worker implements Runnable {
    LinkedBlockingQueue<Request> queueRequests;
    ObjectOutputStream objectOutputStream;

    public Worker(LinkedBlockingQueue<Request> queueRequests, ObjectOutputStream objectOutputStream) {
        this.queueRequests = queueRequests;
        this.objectOutputStream = objectOutputStream;
    }

    public void run() {
        // Get the next request from the queue
        Request request = queueRequests.poll();
        // Process the request
        // ...
        assert request != null;
        long startTime = System.currentTimeMillis();
        double num1 = request.getNum1();
        double num2 = request.getNum2();
        double result = switch (request.getMathFunction()) {
            case ADD -> num1 + num2;
            case SUBTRACT -> num1 - num2;
            case MULTIPLY -> num1 * num2;
            case DIVIDE -> num1 / num2;
        };
        long executionTimeMillis = System.currentTimeMillis() - startTime;
        double executionTimeInSeconds = 1.0*executionTimeMillis / 1000;
        // Add the processing duration to the request result object
        // Return the result and processing time to the client
        // ...
        RequestExecutionResult requestExecutionResult = new RequestExecutionResult(executionTimeInSeconds, result);
        //add result to queue for results?
        //...
        LinkedBlockingQueue<RequestExecutionResult> queueResults;

        queueResults = Server.getQueueResults();

        queueResults.add(requestExecutionResult);

        System.out.println(requestExecutionResult);

        try {
            objectOutputStream.writeObject(requestExecutionResult);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}