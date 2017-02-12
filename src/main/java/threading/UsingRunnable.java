package threading;

public class UsingRunnable implements Runnable {

    public void run() {
        for(int i=0; i<10; i++) {
            System.out.println("Printing " + i);
            try {
                Thread.sleep((int)(Math.random()*100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
