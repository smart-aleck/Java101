package threading;

public class UsingSynchronized extends Thread {

    static int j;

    public UsingSynchronized(){
    }
    public UsingSynchronized(String threadName){
        super(threadName);
    }

    synchronized int increment(){
        return j++;
    }

    public void run() {
        for(int i=0; i<10; i++) {
            System.out.println("Printing [" + this.getName() + "] " + increment());
            try {
                Thread.sleep((int)(Math.random()*100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
