package threading;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

public class UsingWaitNotify {
    Object lockObject = new Object();

    public void producer() throws InterruptedException {
        System.out.println("Starting the producer");
        synchronized (lockObject) {
            Thread.sleep(2000);
            System.out.println("Producer is waiting...");
            lockObject.wait();
            System.out.println("Producer is done waiting...");
        }
    }

    public void consumer() throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("Starting the consumer");
        synchronized (lockObject) {
            System.out.println("The consumer has the lock now");
            new Scanner(System.in).nextLine();
            System.out.println("The consumer is all done");
            lockObject.notify();
        }
    }

    public void execute(){
        Thread producerThread = new Thread(new Runnable() {
            public void run() {
                try {
                    producer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread consmerThread = new Thread(new Runnable() {
            public void run() {
                try {
                    consumer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        producerThread.start();
        consmerThread.start();
    }
}

