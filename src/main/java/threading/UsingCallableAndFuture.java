package threading;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class UsingCallableAndFuture {

    public static void execute(){
        new UsingCallableAndFuture().runAThreadReturningOutput();
    }

    public void runAThreadReturningOutput() {

        ExecutorService pool = Executors.newCachedThreadPool();
        List<Future> listOfResults = new ArrayList<Future>();

        for(int i=0; i<50; i++)
            listOfResults.add(pool.submit(new CallableWithParamater(1000)));

        pool.shutdown();

        try {
            pool.awaitTermination(1, TimeUnit.HOURS);
            for(Future<Integer> result : listOfResults)
                System.out.print(result.get() + " | ");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public class CallableWithParamater implements Callable<Integer> {

        final int MAX_NUM;
        public CallableWithParamater(Integer maxNum){
            MAX_NUM = maxNum;
        }

        public Integer call() {
            return (int)(Math.random() * MAX_NUM);
        }
    }
}
