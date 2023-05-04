package org.example.client;

import org.example.MathFunction;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class RequestGenerator {
    private final Random rand = new Random();
    private int countRes = 0;
    private static final int MAX_COUNT_RES = 15;
    private static int countTotal = 0;

    private final double intervalSeconds;
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

    public static int getCountTotal() {
        return countTotal;
    }

    public void run() {
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
                    countTotal++;
                }

                //generate request
                Request request = generateRequest();

                //send request to server
                Sender.send(request);
            }
        };

        long intervalMillis = (long) (intervalSeconds * 1000);
        // Generate a new ticket with period
        timer.schedule(task, 0, intervalMillis);
    }

    public RequestGenerator(double intervalSeconds) {
        this.intervalSeconds = intervalSeconds;
    }
}
