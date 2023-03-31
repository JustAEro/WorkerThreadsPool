package org.example;

public class RequestExecutionResult {
    private final double executionTimeInSeconds;
    private final double result;
    public RequestExecutionResult(double executionTimeInSeconds, double result){
        this.executionTimeInSeconds = executionTimeInSeconds;
        this.result = result;
    }

    public double getResult() {
        return result;
    }

    public double getExecutionTimeInSeconds() {
        return executionTimeInSeconds;
    }

    @Override
    public String toString() {
        return "RequestExecutionResult{"+
                "executionTimeInSeconds="+executionTimeInSeconds+";"+
                "result="+result+
                "}";
    }
}
