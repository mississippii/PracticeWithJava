package Threading;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class Watch implements Runnable{
    private volatile boolean threadRunning=true;
    @Override
    public void run() {
        while(threadRunning) {
            printCurrentTime();
            sleepOnesecond();
        }
    }
    public void shutdown(){
        this.threadRunning= false;
    }

    private void printCurrentTime() {
       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
             String formatterCurrentTime=  LocalDateTime.now().format(formatter);
       System.out.println(formatterCurrentTime);
    }
    private void sleepOnesecond(){
        try {
            sleep(TimeUnit.SECONDS.toMillis(1));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
