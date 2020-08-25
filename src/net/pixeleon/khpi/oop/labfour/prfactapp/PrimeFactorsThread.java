package net.pixeleon.khpi.oop.labfour.prfactapp;

import javafx.application.Platform;

public class PrimeFactorsThread implements Runnable {

    class WrongRangeException extends Exception {
    }

    private Thread factorsThread;
    private int from;
    private int to;
    private int currentNumber;
    private int lastFoundFactor;
    private double percentage;
    private boolean suspended;
    private boolean finished;
    private final Runnable displayCurrentNumberFunc;
    private final Runnable displayFactorFunc;
    private final Runnable newLineFunc;
    private final Runnable percentageFunc;
    private final Runnable finishFunc;

    public PrimeFactorsThread(Runnable displayCurrentNumberFunc, Runnable displayFactorFunc, Runnable newLineFunc,
                              Runnable percentageFunc, Runnable finishFunc) {
        this.displayCurrentNumberFunc = displayCurrentNumberFunc;
        this.displayFactorFunc = displayFactorFunc;
        this.newLineFunc = newLineFunc;
        this.percentageFunc = percentageFunc;
        this.finishFunc = finishFunc;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    synchronized public double getPercentage() {
        return percentage;
    }

    synchronized public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    synchronized public boolean isSuspended() {
        return suspended;
    }

    synchronized public void setSuspended(boolean suspended) {
        this.suspended = suspended;
    }

    synchronized public boolean isFinished() {
        return finished;
    }

    synchronized public void setFinished(boolean finished) {
        this.finished = finished;
    }

    synchronized public int getLastFoundFactor() {
        return lastFoundFactor;
    }

    synchronized public void setLastFoundFactor(int lastFoundFactor) {
        this.lastFoundFactor = lastFoundFactor;
    }

    synchronized public int getCurrentNumber() {
        return currentNumber;
    }

    synchronized public void setCurrentNumber(int currentNumber) {
        this.currentNumber = currentNumber;
    }

    @Override
    public void run() {
        try {
            checkRange();
        }
        catch (WrongRangeException e) {
            System.err.println(e.getMessage());
            System.err.println("Invalid input. Please check your data for non-integer values.");
        }
        for (int i = from; i <= to; i++) {
            try {
                Thread.sleep(1000);
                setCurrentNumber(i);
                if (displayCurrentNumberFunc != null) {
                    Platform.runLater(displayCurrentNumberFunc);
                }
                int k = i;
                for (int j = 2; j <= Math.sqrt(k); j++) {
                    Thread.sleep(100);
                    if (k % j == 0) {
                        setLastFoundFactor(j);
                        do {
                            if (displayFactorFunc != null) {
                                displayFactorFunc.run();
                            }
                            k /= j;
                        } while (k % j == 0);
                    }
                }
                if (k != 1) {
                    setLastFoundFactor(k);
                    if (displayFactorFunc != null) {
                        Platform.runLater(displayFactorFunc);
                    }
                }
            setPercentage((i - from) / (to - from + 1.0));
            if(percentageFunc != null) {
                Platform.runLater(percentageFunc);
            }
                if (newLineFunc != null) {
                    Platform.runLater(newLineFunc);
                }
            } catch (InterruptedException e) {
                while (isSuspended()) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e1) {
                        if (isFinished()) {
                            break;
                        }
                    }
                }
                if (isFinished()) {
                    break;
                }
            }
        }
        if (finishFunc != null) {
            Platform.runLater(finishFunc);
        }

    }

    private void checkRange() throws WrongRangeException {
        if (getFrom() > getTo() || getFrom() < 2) {
            throw new WrongRangeException();
        }
    }

    public void start() {
        factorsThread = new Thread(this);
        setSuspended(false);
        setFinished(false);
        factorsThread.start();
    }

    public void suspend() {
        factorsThread.interrupt();
        setSuspended(true);
    }

    public void resume() {
        factorsThread.interrupt();
        setSuspended(false);
    }

    public void finish() {
        factorsThread.interrupt();
        setFinished(true);
    }
}
