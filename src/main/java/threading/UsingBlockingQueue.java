package threading;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UsingBlockingQueue {

    BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue(10);
    Random random = new Random();

    public void producer() throws InterruptedException {
        while(true){
            blockingQueue.put(random.nextInt(100));
            Thread.sleep((int)(Math.random()*100));
        }
    }

    public void consumer() throws InterruptedException {
        while(true){
            Thread.sleep((int)(Math.random()*1000));
            Integer queueItem = blockingQueue.take();
            System.out.println("Item: " + queueItem + " | Queue Size: " + blockingQueue.size());
        }
    }

    public void execute(){
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                try {
                    producer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        int workerThreads = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(workerThreads);
        for(int i=0; i<workerThreads; i++){
            executorService.submit(new Runnable() {
                public void run() {
                    try {
                        consumer();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        t1.start();
        executorService.shutdown();
    }
}
