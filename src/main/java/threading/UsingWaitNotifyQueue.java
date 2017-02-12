package threading;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UsingWaitNotifyQueue {
    Queue<Integer> queue = new LinkedList<Integer>();

    public void producer() throws InterruptedException {
        System.out.println("Starting the producer");
        while (true) {
            synchronized (queue) {
                int element = (int) (Math.random() * 100);
                queue.add(element);
                System.out.println("Adding [" + element + "] in the queue. Queue Size [" + queue.size() + "]");
                queue.notify();
            }
            Thread.sleep((int) (Math.random() * 7000));
        }
    }

    public void consumer(int i) throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("Starting the consumer[" + i + "]");
        while (true) {
            synchronized (queue) {
                if (queue.size() <= 0) {
                    System.out.println("Consumer[" + i + "] is waiting as there is nothing on the queue");
                    queue.wait();
                }
                int element = queue.poll();
                System.out.println("Consumer[" + i + "] | Removing [" + element + "] in the queue. Queue Size [" + queue.size() + "]");
            }
            Thread.sleep((int) (Math.random() * 5000));
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
        int numberOfConsumerThreads = 2;
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfConsumerThreads);
        for(int i=0; i<numberOfConsumerThreads; i++){
            executorService.submit(new Runnable() {
                public void run() {
                    try {
                        consumer((int)(Math.random()*100));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        producerThread.start();

        executorService.shutdown();
    }
}

