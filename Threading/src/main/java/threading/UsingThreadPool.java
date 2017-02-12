package threading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class UsingThreadPool {

    public class Process implements Runnable{
        String threadName;
        public Process(int id){
            this.threadName = "[Thread-" + id + "]";
        }
        public void run() {
            System.out.println("Starting " + this.threadName);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Finsihing " + this.threadName);
        }
    }

    ExecutorService executorService = Executors.newFixedThreadPool(2);

    public void execute() throws InterruptedException {
        for(int i=0; i<8; i++) {
            executorService.submit(new Process(i));
        }
        System.out.println(" -- All tasks submitted");

        // Similar to Thread.join - Will wait for 5 seconds here before
        // proceeding to the next instruction
        executorService.awaitTermination(5, TimeUnit.SECONDS);

        // Program will not die if you don't shutdown
        executorService.shutdown();
        System.out.println(" -- Time to say Bye Bye");
    }
}
