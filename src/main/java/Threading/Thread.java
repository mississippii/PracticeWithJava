package Threading;

public class Thread {
    public static void main(String[] args) throws InterruptedException {
       ThreadPool  threadPool = new ThreadPool();
       for(int i=1;i<=10;i++){
           threadPool.processTask(i);
           java.lang.Thread.sleep(10000);
       }
    }
}
