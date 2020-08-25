package net.pixeleon.khpi.oop.labfour;

import java.util.Scanner;

public class PiThread extends Thread {
    private final double eps;
    private int sumElements = 0;
    private double pi = 0;

    public PiThread(double eps) {
        this.eps = eps;
    }

    @Override
    public void run() {
        try {
            byte sign = 1;
            for (double div = 1; 4 / div > eps; div += 2) {
                pi += sign * 4 / div;
                sign *= -1;
                sumElements++;
                System.out.println("Pi = " + pi);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    synchronized public int getSumElements() { return sumElements; }

    public static void main(String[] args) throws InterruptedException {
        PiThread piThread = new PiThread((0.01));
        System.out.println("Press Enter to get number of elements calculated...");
        Thread.sleep(1000);
        piThread.start();
        Scanner scanner = new Scanner(System.in);
        while (piThread.isAlive()) {
            scanner.nextLine();
            System.out.println("Elements calculated so far = " + piThread.getSumElements());
        }
    }
}

