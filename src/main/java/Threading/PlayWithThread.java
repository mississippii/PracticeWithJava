package Threading;

public class PlayWithThread {
    public static void main(String[] args) {
        ThreadClass threadClass = new ThreadClass();
        threadClass.setName("Thread-2");
        threadClass.start();
        for(int i = 0; i <5 ; i++) {
            System.out.println("["+i+"] Inside "+Thread.currentThread().getName());
            ThreadClass.sleepOneSecond();
        }
    }
}
