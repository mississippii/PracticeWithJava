package Threading;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class Watch implements Runnable{
    private boolean threadRunning=true;
    @Override
    public void run() {
        while(true) {
            printCurrentTime();
            sleepOnesecond();
            if(!threadRunning) {
                System.out.println("Thread is interrupted");
                return;
            }
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
            Thread.sleep(TimeUnit.SECONDS.toMillis(1));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
