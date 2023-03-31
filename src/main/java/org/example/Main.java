package org.example;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
    public static void main(String[] args) throws IOException {
        RequestGenerator requestGenerator = new RequestGenerator(1);
        //LinkedBlockingQueue<Request> queueRequests = Server.getQueueRequests();

        //Server server = new Server();
        //server.launch();

        LinkedBlockingQueue<RequestExecutionResult> queueResults = Server.getQueueResults();
        System.out.println("Queue results size = "+queueResults.size());
        for (RequestExecutionResult res: queueResults){
            System.out.println(res);
        }

    }
}
