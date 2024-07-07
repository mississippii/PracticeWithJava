package Threading;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class RunnableClass implements Runnable{
    private FibonacciNuber fibonacciNuber;

    public RunnableClass(int n, String id) {
        fibonacciNuber= new FibonacciNuber(n, id);
    }
    @Override
    public void run() {
        DateTimeFormatter isoFormater = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        System.out.println("Starting Task " + fibonacciNuber.id+" at "+isoFormater.format(LocalDateTime.now()));
        fibonacciNuber.fib(fibonacciNuber.n);
        System.out.println("Ending Task " + fibonacciNuber.id+" at "+isoFormater.format(LocalDateTime.now()));
    }
    public static void sleepOneSecond(){
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(1));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
