package net.pixeleon.khpi.oop.labfour;

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

class Producer implements Runnable {
    private volatile BlockingQueue<Integer> queue;
    private final int numbersToAdd;

    public Producer(BlockingQueue<Integer> queue, int numbersToAdd) {
        this.queue = queue;
        this.numbersToAdd = numbersToAdd;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i < numbersToAdd; i++) {
                queue.offer(i, 100, TimeUnit.MILLISECONDS);
                System.out.println("added number " + i);
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Consumer implements Runnable {
    private volatile BlockingQueue<Integer> queue;
    private Thread producer;
    private int numbersCount = 0;
    private double numbersSum = 0;

    public Consumer(BlockingQueue<Integer> queue, Thread producer) {
        this.queue = queue;
        this.producer = producer;
    }

    @Override
    public void run() {
        try{
            while (producer.isAlive()) {
                numbersSum += queue.take();
                numbersCount++;
                System.out.printf("numbers total = %d, sum = %f, mean = %f%n",
                        numbersCount, numbersSum, numbersSum / numbersCount);
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class BlockingQueueThreads {

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Integer> mainQueue = new LinkedBlockingDeque<>();
        Thread producer = new Thread(new Producer(mainQueue, 100));
        Thread consumer = new Thread(new Consumer(mainQueue, producer));
        producer.setDaemon(true);
        consumer.setDaemon(true);
        Thread.sleep(1000);
        System.out.println("Press Enter to terminate program");
        producer.start();
        consumer.start();
        new Scanner(System.in).nextLine();
        Thread.currentThread().interrupt();
    }
}
