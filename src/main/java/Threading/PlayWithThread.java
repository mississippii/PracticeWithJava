package Threading;

public class PlayWithThread {
    public static void main(String[] args) {
        Watch myThread = new Watch();
        Thread thread = new Thread(myThread);
        thread.start();
    }
}
