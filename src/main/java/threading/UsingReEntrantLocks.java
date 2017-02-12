package threading;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UsingReEntrantLocks {

    public void execute() throws InterruptedException {

        final ThreadRunner threadRunner = new ThreadRunner();
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                threadRunner.executeThread1();
            }
        });
        Thread t2 = new Thread(new Runnable() {
            public void run() {
                threadRunner.executeThread2();
            }
        });
        t1.start();
        t2.start();

        t1.join();
        t2.join();

        threadRunner.finished();
    }

    public class ThreadRunner {

        int count = 0;
        Lock reentrantLock = new ReentrantLock();
        Condition condition = reentrantLock.newCondition();

        private void increment() {
            for (int i = 0; i < 1000000; i++) {
                reentrantLock.lock();
                count++;
                //if(i!=0)  // Application will be stuck in infinite deadlock
                reentrantLock.unlock();
            }
        }

        public void executeThread1() {
            System.out.println("Executing Thread 1");
            System.out.println("I'll let me buddy Thread 2 finish first");
            reentrantLock.lock();
            try {
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("I am Thread 2 and I can starting again");
            increment();
            System.out.println("Finishing Thread 1");
            reentrantLock.unlock();
        }

        public void executeThread2() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            System.out.println("Executing Thread 2");
            reentrantLock.lock();
            increment();
            System.out.println("Finishing Thread 2");
            condition.signal();
            reentrantLock.unlock();
            System.out.println("Thank you Thread 1, I am done, you can continue...");
        }

        public void finished() {
            System.out.println(count);
        }
    }
}
