package threading;

public class ExtendThread extends Thread {

    int j;

    public ExtendThread(String threadName){
        super(threadName);
    }

    public void run() {
        for(int i=0; i<10; i++) {
            System.out.println("Printing [" + this.getName() + "] " + i);
            try {
                Thread.sleep((int)(Math.random()*100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
