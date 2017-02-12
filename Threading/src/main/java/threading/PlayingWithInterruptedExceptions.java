package threading;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class PlayingWithInterruptedExceptions {

    public static void execute() throws InterruptedException {
        new PlayingWithInterruptedExceptions().interruptThread();
    }

    public void interruptThread() throws InterruptedException {
        Thread t = new Thread(new Runnable() {
            public void run() {
                System.out.println("Starting...");
                for(int i=0; i<1E8; i++) {
                    if(Thread.currentThread().isInterrupted()){
                        System.out.println("Interruped at " + i);
                        break;
                    }
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Math.sin(Math.random());
                }

                System.out.println("Exiting");
            }
        });

        t.start();

        Thread.sleep(500);
        t.interrupt();

        t.join();
    }
}
