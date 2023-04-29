package org.example.client;

import org.example.MathFunction;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class RequestGenerator {
    private final Random rand = new Random();
    private int countRes = 0;
    private static final int MAX_COUNT_RES = 5;

    private Request generateRequest() {
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

        return new Request(randomTime,randomMathFunction,randomNum1,randomNum2,result);
    }

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

                //generate request
                Request request = generateRequest();

                System.out.println(request);

                Sender.send(request); //sends request to server
            }
        };

        long intervalMillis = (long) (intervalSeconds * 1000);
        // Generate a new ticket with period
        timer.schedule(task, 0, intervalMillis);
    }
}
