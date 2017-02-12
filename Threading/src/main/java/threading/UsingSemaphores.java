package threading;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class UsingSemaphores {

    public static void execute() throws InterruptedException {

        final UsingSemaphores usingSemaphores = new UsingSemaphores();
        ExecutorService pool = Executors.newCachedThreadPool();

        for(int i=0; i<50; i++){
            Thread.sleep(new Random().nextInt(500));
            pool.submit(new Runnable() {
                public void run() {
                    usingSemaphores.tryToDoWork();
                }
            });
        }
        pool.shutdown();
    }

    Semaphore licensePermits = new Semaphore(10);

    public void tryToDoWork() {
        System.out.println("Thread [" + Thread.currentThread().getName() + "] starting");
        boolean isAcquired = false;
        try {
            isAcquired = licensePermits.tryAcquire();
            if(isAcquired) {
                System.out.println("Thread [" + Thread.currentThread().getName() + "] acquired license, remaining [" + licensePermits.availablePermits() + "]");
                Thread.sleep(new Random().nextInt(5000));
            }
            else{
                System.out.println("Thread [" + Thread.currentThread().getName() + "] NO PERMIT");
            }
        } catch (InterruptedException e) {
        }
        finally {
            if(isAcquired)
                licensePermits.release();
        }
        System.out.println("Thread [" + Thread.currentThread().getName() + "] finished");
    }

    public void doWork() {
        System.out.println("Thread [" + Thread.currentThread().getName() + "] starting");
        try {
            licensePermits.acquire();
            System.out.println("Thread [" + Thread.currentThread().getName() + "] acquired license, remaining [" + licensePermits.availablePermits() + "]");
            Thread.sleep(new Random().nextInt(5000));
        } catch (InterruptedException e) {
        }
        finally {
            licensePermits.release();
        }
        System.out.println("Thread [" + Thread.currentThread().getName() + "] finished");
    }
}
