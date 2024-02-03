package util;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadHandler {

    ExecutorService executor;

    public ThreadHandler() {
      
        executor = Executors.newFixedThreadPool(1);
        
    }

    public Executor getExecutor() {
        return this.executor;
    }
    
    public void start() {
        executor.execute(new Runnable() {

            @Override
            public void run() {
                while(true) {
                    try {
                        System.out.println("Hello");
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            
        });
    }

    public void stop() {
        executor.shutdownNow();
    }


}
