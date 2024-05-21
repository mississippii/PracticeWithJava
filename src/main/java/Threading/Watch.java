package Threading;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class Watch implements Runnable{
    @Override
    public void run() {
        while(true) {
            printCurrentTime();
            sleepOnesecond();
            if(Thread.interrupted()){
                System.out.println("Thread is interrupted");
                return;
            }
        }
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
