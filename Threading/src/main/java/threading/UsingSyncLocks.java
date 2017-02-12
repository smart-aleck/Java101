package threading;

import java.util.ArrayList;
import java.util.List;

public class UsingSyncLocks {

    List<String> list1 = new ArrayList();
    List<String> list2 = new ArrayList();
    Object lock1 = new Object();
    Object lock2 = new Object();

    void addToList1(){
        synchronized(lock1) {
            list1.add("");
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    void addToList2(){
        synchronized(lock2) {
            list2.add("");
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void execute() throws InterruptedException {

        Thread t1 = new Thread(new Runnable() {
            public void run() {
                for(int i=0; i<1000; i++) {
                    addToList1();addToList2();

                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            public void run() {
                for(int i=0; i<1000; i++) {
                    addToList1();addToList2();
                }
            }
        });

        long startTime = System.currentTimeMillis();

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        long endTime = System.currentTimeMillis();

        System.out.println("Total Time: " + (endTime-startTime));
        System.out.println(list1.size());
        System.out.println(list2.size());
    }
}
