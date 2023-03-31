package org.example;

import java.io.Serializable;
import java.net.Socket;

public class Request implements Serializable {
    private final double processingTimeInSeconds;
    private final MathFunction mathFunction;
    private final double num1;
    private final double num2;
    private final double result;

    public Request(double processingTimeInSeconds, MathFunction mathFunction, double num1, double num2, double result){
        this.processingTimeInSeconds = processingTimeInSeconds;
        this.mathFunction = mathFunction;
        this.num1 = num1;
        this.num2 = num2;
        this.result = result;
    }

    public MathFunction getMathFunction() {
        return mathFunction;
    }

    public double getNum1() {
        return num1;
    }

    public double getNum2() {
        return num2;
    }

    public double getProcessingTimeInSeconds() {
        return processingTimeInSeconds;
    }

    public double getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "Request{"+
                "processingTimeInSeconds="+processingTimeInSeconds+";"+
                "mathFunction="+mathFunction+";"+
                "num1="+num1+";"+
                "num2="+num2+";"+
                "result="+result+
                "}";
    }

    //public Request(Socket clientSocket) {
        // Parse the request parameters from the client socket
        // ...

    //}
}
