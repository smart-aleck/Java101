package threading;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UsingCountdownLatch {

    class Process implements Runnable {
        CountDownLatch countDownLatch;
        String id;
        public Process(int id, CountDownLatch countDownLatch){
            this.id = Integer.toString(id);
            this.countDownLatch = countDownLatch;
        }
        public void run() {
            System.out.println(id + " Start - " + countDownLatch.getCount());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            countDownLatch.countDown();
            System.out.println(id + " End - " + countDownLatch.getCount());
        }
    }

    CountDownLatch countDownLatch = new CountDownLatch(5);
    ExecutorService executorService = Executors.newFixedThreadPool(2);

    public void execute() throws InterruptedException {
        System.out.println("Starting");
        for(int i=0; i<countDownLatch.getCount(); i++){
            executorService.submit(new Process(i, countDownLatch));
        }
        executorService.shutdown();
        System.out.println("Executor Shutdown");
        countDownLatch.await();
        System.out.println("Done");
    }
}
