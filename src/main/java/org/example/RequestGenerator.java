package org.example;

import java.io.*;
import java.net.Socket;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class RequestGenerator {
    private final Random rand = new Random();

    private static int countRes = 0;
    private static final int MAX_COUNT_RES = 6;
    public RequestGenerator(double intervalSeconds) {
        Timer timer = new Timer();

        TimerTask task = new TimerTask() {
            public void run() {

                if (countRes >= MAX_COUNT_RES) {
                    timer.cancel();
                    timer.purge();
                    return;
                }
                else {
                    countRes++;
                }
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
                //generate request
                MathFunction[] mathFunctions = MathFunction.values();
                MathFunction randomMathFunction = mathFunctions[rand.nextInt(mathFunctions.length)];
                double randomTime = rand.nextDouble();
                double randomNum1 = rand.nextDouble();
                double randomNum2 = rand.nextDouble();
                double result = switch (randomMathFunction) {
                    case ADD -> randomNum1 + randomNum2;
                    case SUBTRACT -> randomNum1 - randomNum2;
                    case MULTIPLY -> randomNum1 * randomNum2;
                    case DIVIDE -> randomNum1 / randomNum2;
                };

                Request request = new Request(randomTime,randomMathFunction,randomNum1,randomNum2,result);
                System.out.println(request);
                //send generated request to server
                //...
                try {
                    objectOutputStream.writeObject(request);
                    clientSocket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        long intervalMillis = (long) (intervalSeconds * 1000);
        // Generate a new ticket every 5 seconds
        timer.schedule(task, 0, intervalMillis);
    }
}
