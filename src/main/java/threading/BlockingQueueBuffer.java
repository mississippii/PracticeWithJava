package threading;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class BlockingQueueBuffer {
    private BlockingQueue<Integer> queue = new LinkedBlockingDeque<>(10);

    public void addItem(Integer item) {
        try{
            queue.put(item);
        }catch (InterruptedException e){
           Thread.currentThread().interrupt();
           throw new AssertionError(e);
        }
    }

    public Integer getItem(){
        try {
            return queue.take();
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
            throw new AssertionError(e);
        }
    }
}
