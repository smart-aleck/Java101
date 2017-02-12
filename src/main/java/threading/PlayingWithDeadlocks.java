package threading;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PlayingWithDeadlocks {
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

        int count1 = 0;
        int count2 = 0;
        Lock lock1 = new ReentrantLock();
        Lock lock2 = new ReentrantLock();

        private void increment1() {
            for (int i = 0; i < 1000000; i++) {
                count1++;
            }
        }
        private void increment2() {
            for (int i = 0; i < 1000000; i++) {
                count2++;
            }
        }
        private void acquireLocks(Lock lock1, Lock lock2){
            while (true) {
                boolean acquiredLock1 = lock1.tryLock();
                boolean acquiredLock2 = lock2.tryLock();
                if (acquiredLock1 && acquiredLock2)
                    return;
                if (acquiredLock1)
                    lock1.unlock();
                if (acquiredLock2)
                    lock2.unlock();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                }
            }
        }

        public void executeThread1() {
            System.out.println("Starting Thread 1");
            acquireLocks(lock1, lock2);
            try {
                increment1();
                increment2();
            }
            finally {
                lock1.unlock();
                lock2.unlock();
            }
            System.out.println("Finishing Thread 1");
        }

        public void executeThread2() {
            System.out.println("Starting Thread 2");
            acquireLocks(lock2, lock1);
            try {
                increment2();
                increment1();
            }
            finally {
                lock2.unlock();
                lock1.unlock();
            }
            System.out.println("Finishing Thread 2");
        }

        public void finished() {
            System.out.println(count1+count2);
        }
    }
}
