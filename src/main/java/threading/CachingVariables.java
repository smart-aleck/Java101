package threading;

public class CachingVariables extends Thread {

    // volatile guarantees no caching of variables
    volatile boolean running = true;

    public void run(){
        int i=0;
        while(running){
            System.out.println(i++);
            try {
                Thread.sleep((int)(Math.random()*1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopRunning(){
        running = false;
    }
}
