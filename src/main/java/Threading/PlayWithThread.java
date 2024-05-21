package Threading;

public class PlayWithThread {
    public static void main(String[] args) throws InterruptedException {
        Watch myWatch = new Watch();
        Thread thread = new Thread(myWatch);
        thread.start();
        //Thread.sleep(5000);
        //myWatch.shutdown();
    }
}
