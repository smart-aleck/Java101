package threading;

public class DaemonThread extends Thread {
    public DaemonThread(){
        this.setDaemon(true);
    }
    public void run(){
        try {
            while (true) {
                System.out.println("I am running");
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
