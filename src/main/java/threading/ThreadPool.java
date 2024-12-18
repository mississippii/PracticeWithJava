package threading;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPool {
    ThreadPoolExecutor executor = new ThreadPoolExecutor(4,
            4,
            10,
            TimeUnit.MINUTES,
            new ArrayBlockingQueue<>(3),
            new ThreadPoolExecutor.DiscardPolicy() );

    public void processTask(int taskId){
        executor.submit(()->{
            System.out.println("Processing task:"+ taskId+"Completing by");
        });
    }
}
